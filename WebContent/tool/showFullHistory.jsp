<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-bean.tld" prefix="bean"%>



<%@page import="java.util.Properties"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.Writer"%>
<%@page import="java.util.Enumeration"%>



<bbNG:learningSystemPage ctxId="ctx">


	<bbNG:cssFile href="../css/ut_buildingblock.css" />

	<bbNG:breadcrumbBar navItem="UTXA-show_histories_tool-nav-1">
		<bean:define name="SingleHistoryForm" property="history" id="history" />
		<bbNG:breadcrumb> Full History - <bean:write name="history" property="fullName"/> </bbNG:breadcrumb>
	</bbNG:breadcrumbBar>

	<bbNG:jsBlock>
		<SCRIPT LANGUAGE="JavaScript">
			function invalid_eid() {
				return alert("Invalid EID - Enter the UT EID of an enrolled student or select a name from the drop down menu.");
			}
		</SCRIPT>

	</bbNG:jsBlock>

	<bbNG:pageHeader>
		<bbNG:pageTitleBar>  Display Full History </bbNG:pageTitleBar>
	</bbNG:pageHeader>

	<bbNG:actionControlBar>
		<bbNG:actionButton
			url='<%="/webapps/UTXA-show_histories_tool-BBLEARN/tool/showStudentsHistories.do?course_id=" + request.getParameter("course_id")%>'
			title="Back" primary="true" />
	</bbNG:actionControlBar>

	<logic:present name="SingleHistoryForm" property="history">
		<bean:define name="SingleHistoryForm" property="history" id="history" />
		<ul class="history_full">
			<img alt="temp picture"
				src="<bean:write name="history" property="photoLink"/>">
			<li><b><u>NAME:</u></b> <bean:write name="history" property="fullName" /></li>
			<li><b><u>EID:</u></b> <bean:write name="history" property="eid" /></li>
			<logic:notEqual name="history" property="classification"
				value="UNKNOWN">
				<li><b><u>CLASS.:</u></b> <bean:write name="history"
						property="classification" /></li>
			</logic:notEqual>
			<li><b><u>MAJOR:</u></b> <bean:write name="history" property="major" /></li>
			<li><b><u>GPA:</u></b> <bean:write name="history" property="GPA" /></li>
			<li><b><u>SAT SCORES:</u></b> <bean:write name="history" property="totalSAT" /><br>
				<ul>
					<li><u>READING:</u> <bean:write name="history" property="readingSAT" /></li>
					<li><u>MATH:</u>    <bean:write name="history" property="mathSAT" /></li>
					<li><u>WRITING:</u> <bean:write name="history" property="writingSAT" /></li>
				</ul>
			</li>
		</ul>
	</logic:present>

	<%@ include file="commonHidden.jsp"%>
</bbNG:learningSystemPage>
