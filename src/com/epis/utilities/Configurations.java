package com.epis.utilities;

import java.util.ResourceBundle;

import com.epis.common.exception.EPISException;

public class Configurations {

	public Configurations() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static ResourceBundle bundle=null;
	private static Log log = new Log(DBUtility.class);
	static{
		try{
		bundle=ResourceBundle.getBundle("com.epis.resource.dbresource");
		}catch(Exception exp){
			log.error("DBUtility:Exception:Unable to Load com.epis.resource.dbresource propertied file in classpath.");
			log.error("DBUtility:Error:"+exp.getMessage());
		}
	}
	
	public  static String getAccessRightsType() throws EPISException{
		String acrtype="USER";
		if(bundle!=null){
			acrtype=bundle.getString("accessrightstype");
			log.info("Configurations:AccessRightsType:"+acrtype);
		}
		//if(!"USER".equals(acrtype) && !"ROLE".equals(acrtype)){
			//throw new EPISException("Configuration Error!. Check  Access Rights Mapping Configuration. ");
		//}
		return acrtype;
	}

}
