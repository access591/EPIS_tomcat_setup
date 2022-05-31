/**
  * File       : ReadXML.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.epis.bean.rpfc.AirportBean;
import com.epis.utilities.DBUtility;

public class ReadXML {
	DBUtility dbUtils=new DBUtility();

	Log log = new Log(ReadXML.class);
	private NodeList initializeFile(String fileName){
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		NodeList nd=null;
		if(fileName.equals("")){
			fileName="c:\\tree3.xml";
		}
	
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File(fileName));
			doc.getDocumentElement().normalize ();
			nd=doc.getDocumentElement().getChildNodes();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return 	nd;
	}
	
	public ArrayList getRegionalList(String fileName){
		ArrayList regionalNodeLst=new ArrayList();
		ArrayList regionNodeLst=new ArrayList();
		NodeList rootList=this.initializeFile(fileName);
		ArrayList finalNodeList=new ArrayList();
		String regionalValue="";
		regionalNodeLst = this.getRegionalNode(rootList);
		regionNodeLst = this.getRegionalNodeList(regionalNodeLst);
		   for(int i=0; i<regionNodeLst.size(); i++){
			   regionalValue=regionNodeLst.get(i).toString();
			   finalNodeList=this.getAirportNodeList(regionalNodeLst,regionalValue,regionNodeLst);
			   log.info("Size=="+finalNodeList.size());
			   AirportBean airportBean=null;
			   for(int j=0;j<finalNodeList.size()-1;j++){
				   airportBean=(AirportBean)finalNodeList.get(j);
				   if(!airportBean.getAirportCD().equals("") && !airportBean.getAirportName().equals("")){
					   if(this.checkAirportCD(airportBean.getAirportCD(),airportBean.getAirportName(),airportBean.getRegion())!=true){
							this.insertAirportList(airportBean.getAirportCD(),airportBean.getAirportName(),airportBean.getRegion());
						}
				   }
				
		   }
		 
		  
			   
		   }
		   	
		
	
	   return regionNodeLst;
	}

	private ArrayList getRegionalNode(NodeList rootList){
		ArrayList regionNodeLst=new ArrayList(); 
		for(int i=0;i<rootList.getLength();i++){
			Node nds=rootList.item(i);
			NodeList subrootList=nds.getChildNodes();
			for(int nl=0;nl<subrootList.getLength();nl++){
				Node ss=subrootList.item(nl);
				regionNodeLst.add(ss);
			}
		}
		return regionNodeLst;
	}
	
	private ArrayList getRegionalNodeList(ArrayList regionalNodeList){
		System.out.println("getRegionalNodeList()");
		ArrayList regionLst=new ArrayList(); 
		for(int rnl=0;rnl<regionalNodeList.size();rnl++){
			Node ss=(Node)regionalNodeList.get(rnl);
			
			if(ss.getNodeType()==Node.ELEMENT_NODE){
				NamedNodeMap maps=ss.getAttributes();
				for(int k=0;k<maps.getLength();k++){
					Node regionsNode=maps.item(k);
					if(regionsNode.getNodeName().equals("text")){
						
						regionLst.add(regionsNode.getNodeValue());
					}
				}
			}
			
		}
	
		return regionLst;
	}
	private ArrayList getAirportNodeList(ArrayList regionalNodeList,String value,ArrayList regionLst){
		System.out.println("getAirportNodeList");
		AirportBean airportBean=null;
		ArrayList list=new ArrayList();
		String airportValue="",airportID="";
		NodeList regionNodeList=null;
		boolean regionFlag=false;
		for(int rnl=0;rnl<regionalNodeList.size();rnl++){
			Node ss=(Node)regionalNodeList.get(rnl);
			if(ss.getNodeType()==Node.ELEMENT_NODE){
				NamedNodeMap maps=ss.getAttributes();
				for(int k=0;k<maps.getLength();k++){
					Node regionsNode=maps.item(k);
					if(regionsNode.getNodeName().equals("text")&& regionsNode.getNodeValue().equals(value)){
						regionFlag=true;
						regionNodeList=ss.getChildNodes();
					}
				}
			}
		}
		if (regionFlag == true) {
			for (int nl = 0; nl < regionNodeList.getLength(); nl++) {
				Node airportsNode = regionNodeList.item(nl);
				airportBean = new AirportBean();

				if (airportsNode.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap regionMaps = airportsNode.getAttributes();
					for (int rmaps = 0; rmaps < regionMaps.getLength(); rmaps++) {
						Node airportNodes = regionMaps.item(rmaps);
						if (airportNodes.getNodeName().equals("text")) {
							airportValue = airportNodes.getNodeValue();
							airportBean.setAirportName(airportValue);
							
						}
						if (airportNodes.getNodeName().equals("id")) {
							airportID = airportNodes.getNodeValue();
							airportBean.setAirportCD(airportID);
						}
						airportBean.setRegion(value);
					}
					
				}
				list.add(airportBean);
			}
		}
	return list;
	}
	
	private void insertAirportList(String airportCD,String airportValue,String regionList){
		Connection con = null;
		Statement st = null;
		
		try {
			
			String insAirportlst="insert into employee_unit_master(UNITCODE,UNITNAME,REGION)  VALUES ('"+airportCD+"','"+airportValue+"','"+regionList+"')";
			con = dbUtils.getConnection();
			st = con.createStatement();
			st.executeUpdate(insAirportlst);
				
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeConnection(con, st, null);
		}
	}
	private boolean checkAirportCD(String airportCD,String airportValue,String regionList){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		boolean flag=false;
		try {
			
			String chkAirportlst="SELECT COUNT(*) AS COUNT FROM employee_unit_master WHERE UNITCODE='"+airportCD+"' AND UNITNAME='"+airportValue+"' AND REGION='"+regionList+"'";
			con = dbUtils.getConnection();
			st = con.createStatement();
			rs=st.executeQuery(chkAirportlst);
			if(rs.next()){
				if(rs.getInt("COUNT")!=0){
					flag=true;
				}
			}
				
		log.info("COUNT==============="+rs.getInt("COUNT"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		return flag;
	}
	public int countAirportSize(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		boolean flag=false;
		int count=0;
		try {
			
			String chkAirportlst="SELECT COUNT(*) AS COUNT FROM employee_unit_master";
			con = dbUtils.getConnection();
			st = con.createStatement();
			rs=st.executeQuery(chkAirportlst);
			if(rs.next()){
				count=rs.getInt("COUNT");
			}
				

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		return count;
	}
	public void closeConnection(Connection con, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException se) {
				System.out.println("Problem in closing Resultset ");
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException se) {
				System.out.println("Problem in closing Statement.");
			}
		}

		try {
			if (con != null && !con.isClosed()) {
				con.close();
				con = null;
			}
		} catch (SQLException se) {
			System.out.println("Problem in closing Connection.");
		}

	}
}
