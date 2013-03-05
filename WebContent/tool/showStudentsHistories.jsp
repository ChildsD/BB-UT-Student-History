<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-bean.tld" prefix="bean"%>

<%@page import="java.util.*"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.Writer"%>
<%@ page import="java.awt.*,org.jCharts.*,org.jCharts.chartData.*,org.jCharts.properties.*,org.jCharts.types.ChartType,org.jCharts.axisChart.*,org.jCharts.test.TestDataGenerator,org.jCharts.encoders.JPEGEncoder13,org.jCharts.properties.util.ChartFont,
					  org.jCharts.encoders.ServletEncoderHelper"%>


<bbNG:learningSystemPage ctxId="ctx">

	<bbNG:cssFile href="../css/ut_buildingblock.css" />

	<bbNG:breadcrumbBar navItem="UTXA-show_histories_tool-nav-2">
		<bbNG:breadcrumb> Show Student Histories </bbNG:breadcrumb>
	</bbNG:breadcrumbBar>

	<bbNG:jsBlock>
		<SCRIPT LANGUAGE="JavaScript">
			function invalid_eid() {
				return alert("Invalid EID - Enter the UT EID of an enrolled student or select a name from the drop down menu.");
			}
		</SCRIPT>
	</bbNG:jsBlock>

	<bbNG:pageHeader>
		<bbNG:pageTitleBar>  Display Student Histories </bbNG:pageTitleBar>
	</bbNG:pageHeader>

	<div class="head">
		<p>Use the pull-down menu or click a student to view a more
			detailed academic history.</p>
		<%@ include file="contact.jsp"%>

		<html:form
			action='<%="/tool/showFullHistory.do?course_id=" + request.getParameter("course_id")+"&populate=1"%>'>
			<bbUI:nonce nonceId="BB_NONCE" />
			<b>Select a student: </b>
			<html:select name="SingleHistoryForm" property="eid"
				onchange="submit()">
				<html:option value="SHOW ALL" />
				<!-- IS THIS BAD/INSECURE IN ANY WAY? -->
				<html:options collection="theEnrollmentList" property="eid"
					labelProperty="fullName" />
			</html:select>
		</html:form>

		<html:form
			action='<%="/tool/showStudentsHistories.do?course_id=" + request.getParameter("course_id")%>'>
			<bbUI:nonce nonceId="BB_NONCE" />
			<b>Sort by: </b>
			<html:select name="HistoriesForm" property="sort" onchange="submit()">
				<html:option value="Select" />
				<html:option value="Name" />
				<html:option value="GPA" />
				<html:option value="SAT" />
			</html:select>
		</html:form>
		<strong>Average GPA = <bean:write name="HistoriesForm"
				property="GPA" /></strong>
	</div>
	
	<%-- <% ServletEncoderHelper.encodeJPEG13(arg0, arg1, arg2) %> I NEED TO FIGURE OUT WHAT I'M DOING HERE	LOOK AT THE EXAMPLE JSP FILE THAT I DOWNLOADED --%>


	<div class="grid">
		<logic:iterate name="HistoriesForm" property="histories" id="history">
			<bean:define name="history" property="eid" id="eid" />
			<html:link
				action='<%="/tool/showFullHistory.do?course_id=" + request.getParameter("course_id")+"&populate=2"+"&eid="+eid%>'
				onclick="submit()">
				<bbUI:nonce nonceId="BB_NONCE" /> <!-- IS THIS RIGHT?!?! -->
				<ul class="history">
					<img alt="temp picture"
						src="<bean:write name="history" property="photoLink"/>">
					<li><b><u>NAME: </u></b> <bean:write name="history"
							property="fullName" /></li>
					<li><b><u>EID: </u></b> <bean:write name="history"
							property="eid" /></li>
					<li><b><u>GPA: </u></b> <bean:write name="history"
							property="GPA" /></li>
					<li><b><u>SAT SCORES: </u></b> <bean:write name="history"
							property="totalSAT" /><br></li>
					<%-- <li><b><u>EAWS Status: </u></b> <bean:write name="history"
							property="eawsRiskLevel" /><br></li> --%>
							<logic:match name="history" property="classification" value="FRESHMAN">
							<li><b><u>FRESHMAN</u></b><br></li>
							</logic:match>
							
				</ul>
			</html:link>
		</logic:iterate>
	</div>
	<%@ include file="commonHidden.jsp"%>
</bbNG:learningSystemPage>
