<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.*"%>
<%@ page import="java.util.ArrayList" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
</head>
<%
	ArrayList formList=new ArrayList();
	ArrayList nomineeList=new ArrayList();
	EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
	EmployeeAdlPensionInfo adlPersonInfo=new EmployeeAdlPensionInfo();
	if(request.getAttribute("perForm10DList")!=null){
		formList=(ArrayList)request.getAttribute("perForm10DList");
	}

	personalInfo=(EmployeePersonalInfo)formList.get(0);
	nomineeList=(ArrayList)formList.get(2);
	adlPersonInfo=(EmployeeAdlPensionInfo)formList.get(5);
%>

<body>
<table  width="100%"align="center" cellpadding="1" cellspacing="1" border="0"> 

	

  <tr>
  	<td >
		<table align="center" width="100%" height="50" cellpadding="2" cellspacing="2" border="0">
			<tr>	
				<td width="50%" class="reportsublabel">In case of Reduced Pension (Early Pension)</td>		
				<td><%=adlPersonInfo.getEarlyPensionTaken()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">a.Date Of Option for Commencement of pension:</td>		
				<td><%=adlPersonInfo.getEarlyPensionDate()%></td>		
			</tr>
			<tr>	
				<td colspan="2" class="reportsublabel">Member can exercise option in case of early Pension indicating the date of option for commencement of pension from </td>		
				
			</tr>
			<tr>	
				<td class="reportsublabel">i.)Date of submission of Form-10</td>		
				<td><%=adlPersonInfo.getEpForm10DSubDate()%></td>		
			</tr>
			<tr>	
				<td align="center" class="reportsublabel">OR</td>		
				<td>&nbsp;</td>		
			</tr>
			<tr>	
				<td class="reportsublabel">ii.)Any prospective date between submission of claim form completion of 58 Years of age </td>		
				<td class="Data">&nbsp;</td>		
			</tr>
			<tr>	
				<td class="reportsublabel">9.Option for commutation of 1/3 of quantum<br /> Pension(If option is for lesser)commutation indicate the quantum</td>		
				<td class="Data"><%=adlPersonInfo.getQuantum1By3Option()%>
				<% if(adlPersonInfo.getQuantum1By3Option().toUpperCase().equals("Y")){%>
					Amount:<%=adlPersonInfo.getQuantum1By3Amount()%>
					
				<%}%>
				</td>		
			</tr>
			<tr>	
				<td class="reportsublabel">10.Option of Return of Capital(Please refer Serial Number 10 of Instructions)[Put a Tick]</td>		 
				<td class="Data"><%=adlPersonInfo.getOptionReturnCaptial()%></td>		
			</tr>
			
			<tr>
				<td class="reportsublabel">If yes,indicate your choice of alternative</td>
				<td class="Data">1/2/3</td>
			</tr>
			<tr>
				<td class="reportsublabel">11.Mention your Nominee for Return of Capital</td>
				<td>&nbsp;</td>		
			</tr>
			<tr>
				<td colspan="2">
					<table cellpadding="2" cellspacing="2" border="1" width="80%" height="120" align="center">
						<tr>
						<td class="reportsublabel">Name		</td>
						<td class="reportsublabel">Relationship</td>
						<td class="reportsublabel">DateOfBirth	</td>
						<td class="reportsublabel">Address		</td>
						</tr>
						<%if(nomineeList.size()!=0){
						NomineeBean nomineeBean=new NomineeBean();
						for(int nomlst=0;nomlst<nomineeList.size();nomlst++){
						nomineeBean=(NomineeBean)nomineeList.get(nomlst);
						System.out.println("Nominee Return Flag"+nomineeBean.getNomineeReturnFlag());
						if(nomineeBean.getNomineeReturnFlag().equals("Y")){%>
								<tr>
									<td class="Data"><%=nomineeBean.getNomineeName()%></td>
									<td class="Data"><%=nomineeBean.getNomineeRelation()%></td>
									<td class="Data"><%=nomineeBean.getNomineeDob()%></td>
									<td class="Data"><%=nomineeBean.getNomineeAddress()%></td>
						</tr>
						<%}}}%>
					
						
					</table>
				</td>
				
			</tr>
			</table>
			</td>
			</tr>


</table>
</body>
</html>
