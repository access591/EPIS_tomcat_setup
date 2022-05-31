 /**
  * File       : Pagenation.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities;

import com.epis.bean.rpfc.BottomGridNavigationInfo;
import com.epis.utilities.Constants;
import com.epis.utilities.GridNavigation;
import com.epis.utilities.LoadProperties;
import com.epis.utilities.Log;
public class Pagenation implements Constants{
	Log log=new Log(Pagenation.class);
	protected BottomGridNavigationInfo bottomGrid=null;
	protected String statusNext="";
	protected String statusLast="";
	protected String statusFirst="";
	protected String statusPrevious="";
	protected int pageIndex=0;
	protected GridNavigation gridNavigation = null;
	protected static LoadProperties getProperties = null;
	//protected static int gridLength=getGridLength();
	public BottomGridNavigationInfo searchPagination(int totalRecords,int startIndex,int gridLength){
		bottomGrid=new BottomGridNavigationInfo();
		if (totalRecords < gridLength) {
			statusNext = "disabled";
			statusLast = "disabled";
		}
		if (startIndex == 1) {
			statusFirst = "disabled";
			statusPrevious = "disabled";
		} else if (startIndex == ((totalRecords - gridLength) + 1) || 
				startIndex == totalRecords - (totalRecords % gridLength) + 1) {
			statusNext = "disabled";
			statusLast = "disabled";
		}
		bottomGrid.setStatusFirst(statusFirst);
		bottomGrid.setStatusNext(statusNext);
		bottomGrid.setStatusPrevious(statusPrevious);
		bottomGrid.setStatusLast(statusLast);
		return bottomGrid;
	}
	public BottomGridNavigationInfo navigationPagination(int totalRecords,int startIndex,boolean onlyFirst,boolean onlyLast,int gridLength){
		bottomGrid = new BottomGridNavigationInfo();
		
		if (startIndex == totalRecords || (startIndex < totalRecords && startIndex > totalRecords - gridLength))
			onlyFirst = true;
		if (startIndex == 1) {
			onlyLast = true;
		}
		if (onlyFirst && !onlyLast) {
			statusNext = "disabled";
			statusLast = "disabled";
		}
		if (!onlyFirst && onlyLast) {
			statusFirst = "disabled";
			statusPrevious = "disabled";
		}
		if (onlyFirst && onlyLast) {
			statusFirst = "";
			statusPrevious = "";
			statusNext = "";
			statusLast = "";
		}
		bottomGrid.setStatusFirst(statusFirst);
		bottomGrid.setStatusNext(statusNext);
		bottomGrid.setStatusPrevious(statusPrevious);
		bottomGrid.setStatusLast(statusLast);
		return bottomGrid;
	}
	public int getPageIndex(String navButton,int startIndex,int totalRecords,int gridLength ){
		gridNavigation=new GridNavigation();
		pageIndex=gridNavigation.startIndex(navButton, startIndex,gridLength, totalRecords);
		return pageIndex;
	}
	/*public static int getGridLength(){
		
		getProperties=new LoadProperties();
		Properties prop=new Properties();
		prop=getProperties.loadFile(Constants.APPLICATION_PROPERTIES_FILE_NAME);
		if(prop.getProperty("common.gridlength")!=null){
			gridLength=Integer.parseInt(prop.getProperty("common.gridlength"));
		}
		
			return gridLength;
	}*/
}
