
package com.epis.utilities;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import com.epis.bean.admin.ScreenOptionsBean;
import com.epis.dao.admin.AccessRightsDAO;
import com.epis.info.login.LoginInfo;

public class ScreenUtilities{
	private static ResourceBundle bundle=null;
	private static Log log = new Log(DBUtility.class);
	static{
		try{
		bundle=ResourceBundle.getBundle("com.epis.resource.ApplicationResources");
		}catch(Exception exp){
			log.error("DBUtility:Exception:Unable to Load com.epis.resource.ApplicationResources propertied file in classpath.");
			log.error("DBUtility:Error:"+exp.getMessage());
		}
	}
	
	public static String screenHeader(String title){
		String screentitle=title;
		if(bundle!=null){
			try{
			screentitle=bundle.getString(title);
			}catch(Exception e){
				log.error("ScreenUtilities:screenHeader:Exception:"+e.getMessage());
			}
		}
		StringBuffer screenheader=new StringBuffer("");
		screenheader.append("<table width='100%' border='0' cellspacing='15' cellpadding='0'>");
		screenheader.append("<tr>");
		screenheader.append("<td width='100%' align='left' valign='top'><table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		screenheader.append("<tr>");
		screenheader.append(" <td align='left' valign='top'><table width='100%' cellpadding='0' cellspacing='0' border='0'>");
		screenheader.append("<tr>");
		screenheader.append("<td width='4' class='crv_TL'>&nbsp;</td>");
		screenheader.append("<td width='100%' class='crv_lineT'>"+screentitle+"</td>");
		screenheader.append("<td width='4' align='right' class='crv_TR'>&nbsp;</td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append("<td class='crv_lineL'><img src='images/spacer.gif' width='2' height='1' border='0'></td>");
		screenheader.append("<td class='crv_M'><table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableText'>");
		screenheader.append("<tr>");
		screenheader.append("<td height='50' align='center' valign='top'>");
		
		return screenheader.toString();
		
	}
	public static String screenFooter( ){
		
		StringBuffer screenheader=new StringBuffer("");
		screenheader.append("</td>");
		screenheader.append(" </tr>");
		screenheader.append("</table></td>");
		screenheader.append("<td class='crv_lineR'><img src='images/spacer.gif' width='2' height='1' border='0'></td>");
		screenheader.append(" </tr>");
		screenheader.append(" <tr>");
		screenheader.append(" <td class='crv_BL'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("<td valign='top' class='crv_lineB'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("<td class='crv_BR'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("</tr>");
		screenheader.append("</table></td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append("<td align='left' valign='top'>&nbsp;</td>");
		screenheader.append("</tr>");
		screenheader.append("</table></td>");
		screenheader.append("</tr>  ");    
     	screenheader.append("</table>");
		return screenheader.toString();
		
	}
	
	public static String saearchHeader(String title){
		String screentitle=title;
		if(bundle!=null){
			try{
				screentitle=bundle.getString(title);
				}catch(Exception e){
					log.error("ScreenUtilities:screenHeader:Exception:"+e.getMessage());
				}
			
		}
		StringBuffer screenheader=new StringBuffer("");
		screenheader.append("<table width='100%' border='0' cellspacing='15' cellpadding='0'>");
		screenheader.append(" <tr>");
		screenheader.append("<td width='100%' align='left' valign='top'><table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		screenheader.append("<tr>");
		screenheader.append("<td align='left' valign='top'><table width='100%' cellpadding='0' cellspacing='0' border='0'>");
		screenheader.append("<tr>");
		screenheader.append("<td width='4' class='crv_TL'>&nbsp;</td>");
		screenheader.append("<td width='100%' class='crv_lineT'>"+screentitle+"</td>");
		screenheader.append("<td width='4' align='right' class='crv_TR'>&nbsp;</td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append("<td class='crv_lineL'><img src='images/spacer.gif' width='2' height='1' border='0'></td>");
		screenheader.append("<td class='crv_M'><table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableText'>");
		screenheader.append("<tr>");
		screenheader.append(" <td height='50' align='center' valign='top'>");
		return screenheader.toString();
		
	}
	public static String searchSeperator(){
		
		StringBuffer screenheader=new StringBuffer("");
		screenheader.append("</td>");
		screenheader.append("</tr>");
		screenheader.append("</table></td>");
		screenheader.append("<td class='crv_lineR'><img src='images/spacer.gif' width='2' height='1' border='0'></td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append("<td class='crv_BL'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("<td valign='top' class='crv_lineB'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("<td class='crv_BR'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("</tr>");
		screenheader.append("</table></td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append("  <td align='left' valign='top'>&nbsp;</td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append("  <td align='left' valign='top'><table width='100%' cellpadding='0' cellspacing='0' border='0'>");
		screenheader.append("    <tr>");
    	screenheader.append("      <td class='crv_L'><img src='images/spacer.gif' width='4' height='4' border='0' /></td>");
	    screenheader.append("      <td width='100%' class='crv_lineTop'><img src='images/spacer.gif' width='4' height='4' border='0' /></td>");
		screenheader.append("      <td width='4' align='right' class='crv_R'><img src='images/spacer.gif' width='4' height='4' border='0' /></td>");
		screenheader.append("    </tr>");
		screenheader.append("    <tr>");
		screenheader.append("      <td class='crv_lineL'><img src='images/spacer.gif' width='2' height='1' border='0' /></td>");
		screenheader.append("      <td align='left' valign='top' class='crv_M'><table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		screenheader.append("        <tr>");
		screenheader.append("          <td height='50' align='left' valign='top'>");
		return screenheader.toString();
		
	}
	
	public static String searchFooter(){
		
		StringBuffer screenheader=new StringBuffer("");
		screenheader.append("</td>");
		screenheader.append(" </tr>");
		screenheader.append(" </table></td>");
		screenheader.append(" <td class='crv_lineR'><img src='images/spacer.gif' width='2' height='1' border='0' /></td>");
		screenheader.append(" </tr>");
		screenheader.append(" <tr>");
		screenheader.append("  <td class='crv_BL'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("  <td valign='top' class='crv_lineB'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append("  <td class='crv_BR'><img src='images/spacer.gif' height='3' /></td>");
		screenheader.append(" </tr>");
		screenheader.append(" </table></td>");
		screenheader.append("</tr>");
		screenheader.append("<tr>");
		screenheader.append(" <td align='left' valign='top'>&nbsp;</td>");
		screenheader.append("</tr>");
		screenheader.append("</table></td>");
		screenheader.append("</tr>     "); 
		screenheader.append("</table>");
		return screenheader.toString();
		
	}
	
	public static String  getAcessOptions(HttpSession session, int tabindex){
		StringBuffer acessOptions=new StringBuffer("");
		LoginInfo loginInfo=(LoginInfo)session.getAttribute("user");
		AccessRightsDAO acdao=AccessRightsDAO.getInstance();
		String userName="";
		String screenCode="";
		if(loginInfo!=null){
			userName=loginInfo.getUserName();
		}
		screenCode=(String)session.getAttribute(Constants.CURRENT_ACCESSED_SCREEN_CODE);
	
		acessOptions.append("<table width='155' border='0' cellspacing='1' cellpadding='1'>");
		acessOptions.append("<tr>");    
		String errorMessage="";
		List optionsList=null;
		try{
		 optionsList=acdao.getAccessOptions(screenCode,userName);
		}catch(Exception e){
			errorMessage=e.getMessage();
		}
		if(optionsList!=null){
			ScreenOptionsBean sobean=null;
			String onblur="";
			int count=optionsList.size();
			for(int opcnt=0;opcnt<optionsList.size();opcnt++){
				sobean=(ScreenOptionsBean)optionsList.get(opcnt);
				System.out.println(sobean);
				if(opcnt==(count-1))
					onblur="onblur='checkBoxFocus(document.forms[0].selectall);'";
				if(sobean!=null){
					acessOptions.append(" <td width='40'><input type='button' name='").append(sobean.getOptionName()).append("' value='").append(sobean.getOptionName()).append("' tabindex='"+tabindex+"' Class='btn' onclick=\"selectCheckboxes('").append(sobean.getOptionName()).append("');\"  "+("Y".equals(sobean.getAccessRight())?"":"disabled")+ " "+onblur+" " +"></td>");
				}
				
				tabindex++;
			}
		}		        
          acessOptions.append(" </tr>");
          acessOptions.append(" </table>");
          
          //System.out.println(acessOptions.toString()+"  Error "+errorMessage);
          
		return acessOptions.toString();
	}
	public static String  getAcessOptions(HttpSession session, int tabindex,boolean isFirstOptionAsSubmit){
		StringBuffer acessOptions=new StringBuffer("");
		LoginInfo loginInfo=(LoginInfo)session.getAttribute("user");
		AccessRightsDAO acdao=AccessRightsDAO.getInstance();
		String userName="";
		String screenCode="";
		if(loginInfo!=null){
			userName=loginInfo.getUserName();
		}
		screenCode=(String)session.getAttribute(Constants.CURRENT_ACCESSED_SCREEN_CODE);
	
		acessOptions.append("<table width='155' border='0' cellspacing='0' cellpadding='0'>");
		acessOptions.append("<tr>");    
		String errorMessage="";
		List optionsList=null;
		try{
		 optionsList=acdao.getAccessOptions(screenCode,userName);
		}catch(Exception e){
			errorMessage=e.getMessage();
		}
		if(optionsList!=null){
			ScreenOptionsBean sobean=null;
			for(int opcnt=0;opcnt<optionsList.size();opcnt++){
				sobean=(ScreenOptionsBean)optionsList.get(opcnt);
				System.out.println(sobean);
				if(sobean!=null){
					if(opcnt==0&&isFirstOptionAsSubmit){
					acessOptions.append(" <td width='40'><input type='submit' name='").append(sobean.getOptionName()).append("' value='").append(sobean.getOptionName()).append("' tabindex='"+tabindex+"' Class='btn'   "+("Y".equals(sobean.getAccessRight())?"":"disabled")+"></td>");
					}else{
						acessOptions.append(" <td width='40'><input type='button' name='").append(sobean.getOptionName()).append("' value='").append(sobean.getOptionName()).append("' tabindex='"+tabindex+"' Class='btn' onclick=\"selectCheckboxes('").append(sobean.getOptionName()).append("');\"  "+("Y".equals(sobean.getAccessRight())?"":"disabled")+"></td>");
					}
				}
				
				tabindex++;
			}
		}		        
          acessOptions.append(" </tr>");
          acessOptions.append(" </table>");
          
          //System.out.println(acessOptions.toString()+"  Error "+errorMessage);
          
		return acessOptions.toString();
	}

	
}
