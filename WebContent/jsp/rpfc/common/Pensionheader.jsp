<!--
/*
  * File       : PensionCommon.jsp
  * Date       : 13/07/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 <body>
  <table cellSpacing="0" cellPadding="0" border="0" width="100%">
			<tr>
				<td colspan="6">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  background="<%=basePath%>PensionView/images/topbg.gif">
						<tr>
							<td width="7%" align="left" valign="top">
								<img src="<%=basePath%>PensionView/images/aai_logo.gif" width="55" height="48" hspace="10" />
							</td>
							<td>
								<img src="<%=basePath%>PensionView/images/epis_title.gif" border="0" />
							</td>
							<td width="17%" align="right" valign="middle">
								<img src="<%=basePath%>PensionView/images/epis_logo.gif" width="143" height="31" hspace="10" />
							</td>
						</tr>
					</table>
				</td>

			</tr>
  </TABLE>
  </body>
</html>
