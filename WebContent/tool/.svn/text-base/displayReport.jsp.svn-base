<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>

<%@ taglib uri="/bbNG" prefix="bbNG"%>

<bbNG:genericPage title="Edit Grades Report">
		<bbNG:breadcrumbBar navItem="UTXA-edit_gb_tool-nav-1">
			<bbNG:breadcrumb>
				Report
			</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>

		<bbNG:receipt title="Edit Grades Report" recallUrl='<%="/webapps/blackboard/execute/courseMain?course_id=" + request.getParameter("course_id")%>'>
				You may review the results that have been uploaded at  <a HREF='<%="/webapps/portal/frameset.jsp?tab_tab_group_id=_3_1&url=/webapps/gradebook/do/instructor/enterGradeCenter?course_id=" + request.getParameter("course_id") %>' >  Grade Center View </a>
				<BR> <BR>
				<FONT color="#800000">
					ALERT: Please check your grades in the Grade Center. If the upload is not correct, email us at <A HREF="mailto:blackboard@austin.utexas.edu"> Blackboard Support </A> immediately. 
				</FONT>
				<br>
				<br>
				
				<%@ include file="contact.jsp"%>
				<%@ include file="commonHidden.jsp"%>
		</bbNG:receipt>

</bbNG:genericPage>
