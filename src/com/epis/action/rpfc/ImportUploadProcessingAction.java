package com.epis.action.rpfc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.RequestBean;
import com.epis.dao.CommonDAO;

import com.epis.services.rpfc.PensionService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;

public class ImportUploadProcessingAction extends CommonRPFCAction {
	Log log=new Log(ImportUploadProcessingAction.class);
	CommonUtil commonUtil = new CommonUtil();
	
	CommonDAO commonDAO = new CommonDAO();
	
	PensionService ps = new PensionService();
	
	public ActionForward loadImportedProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map monthMap = new LinkedHashMap();
		Map formsMap = new LinkedHashMap();
		String formName = "", returnPage = "", screenTitle = "";
		Iterator monthIterator = null;
		Iterator formsListMap = null;
		if (request.getParameter("frmName") != null) {
			formName = request.getParameter("frmName");
		}
		monthMap = commonUtil.getMonthsList();
		Set monthset = monthMap.entrySet();
		monthIterator = monthset.iterator();
		if (formName.equals("supplementary")) {
			formsMap.put("AAIEPF-Other", "AAIEPF-Other (Supplementary Data)");
			screenTitle = "Upload Supplementary Data";
			returnPage = "loadsuppimportform";
		} else {
			formsMap = commonUtil.getFormsList();
			screenTitle = "Upload Monthly CPF Recoveries Information";
			returnPage = "loadimportform";
		}
		log.info("formName=============================="+formName);
		Set formSet = formsMap.entrySet();
		formsListMap = formSet.iterator();
		request.setAttribute("monthIterator", monthIterator);
		request.setAttribute("frmName", formName);
		request.setAttribute("screenTitles", screenTitle);
		loadPrimarilyInfo(request, returnPage);
		request.setAttribute("formsListMap", formsListMap);
		
		return mapping.findForward(returnPage);
	}
	
	public ActionForward uploadProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dyna=(DynaValidatorForm)form;
		String errorMessages="";
		String lengths = "", xlsSize = "",message="", insertedSize = "", txtFileSize = "", invalidTxtSize = "",region="",airportcode="",year="",month="";
		String[] temp;
		FormFile myFile =(FormFile) dyna.get("uploadfile");
		ResourceBundle bundle = ResourceBundle
		.getBundle("com.epis.resource.dbresource");
		String folderPath = bundle.getString("upload.folder.path");
		String contentType="",fileName="",frmFileName="",formName="";
		if(request.getParameter("frmName")!=null){
			formName=request.getParameter("frmName");
		}
		contentType = myFile.getContentType();
        frmFileName = myFile.getFileName();
        int fileSize= myFile.getFileSize();
        byte[] fileData=new byte[fileSize];
    	log.info("uploadProcess::contentType"+contentType);
		log.info("uploadProcess::fileName"+fileName);
		log.info("uploadProcess::fileData"+fileData);
        try {
			fileData    = myFile.getFileData();
			File saveFilePath = new File(folderPath);
			if (!saveFilePath.exists()) {
				File saveDir = new File(folderPath);
				if (!saveDir.exists())
					saveDir.mkdirs();
			}
			fileName = folderPath + "\\" + frmFileName;
		
			FileOutputStream fileOut = new FileOutputStream(fileName);
			fileOut.write(fileData); 
			String userName = "", ipAddress = "";
			RequestBean requestInfo = new RequestBean(request);
			
			userName = requestInfo.getLoginUserName();
			
			if (request.getParameter("frm_region") != null) {
				region = request.getParameter("frm_region").toString();
			}
			if (request.getParameter("airPortCode") != null) {
				airportcode = request.getParameter("airPortCode")
				.toString();
			}
			log.info("airportcode " + airportcode);
			if (request.getParameter("year") != null) {
				year = request.getParameter("year").toString();
			}
			if (request.getParameter("month") != null) {
				month = request.getParameter("month").toString();
			}
			
			message = ps.readImportData(fileName, userName,
					ipAddress, region, year, month, airportcode);
	/*		temp = lengths.split(",");
			request.setAttribute("lengths", lengths);
			request.setAttribute("message", message);*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			message="File Not Found";
			log.printStackTrace(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message=e.getMessage();
			log.printStackTrace(e);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			message=e.getMessage();
			log.info("errorMessages==================="+message);
			log.printStackTrace(e);
		}
		request.setAttribute("frmName",formName);
		ActionMessages actionMessage = new ActionMessages();
		actionMessage.add("message", new ActionMessage("common.upload.process.tr.message",message));
		saveMessages(request, actionMessage);
		return loadImportedProcess(mapping,form,request,response); 
	}
}
