<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/config/tlds/struts-bean.tld" prefix="bean"%>

<bbNG:learningSystemPage  ctxId="ctx">

		<bbNG:breadcrumbBar navItem="UTXA-edit_gb_tool-nav-1">
			<bbNG:breadcrumb> Error </bbNG:breadcrumb>
		</bbNG:breadcrumbBar>

		<bbNG:receipt title="Edit Grades Tool"  recallUrl='<%="/bin/common/control_panel.pl?course_id=" + request.getParameter("course_id")%>'>
				<html:messages id="msg" message="true" property="message">
					<bean:write name="msg" filter="false" />
				</html:messages>
				<%@ include file="commonHidden.jsp"%>
		</bbNG:receipt>	

</bbNG:learningSystemPage>
