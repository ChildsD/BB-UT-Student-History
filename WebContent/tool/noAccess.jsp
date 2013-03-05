<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<bbNG:genericPage title="Edit/Delete Admin">
	<bbNG:breadcrumbBar navItem="UTXA-edit_gb_tool-nav-1">
		<bbNG:breadcrumb>
			Access Denied
		</bbNG:breadcrumb>
	</bbNG:breadcrumbBar>
	
	<br>
	<bbNG:receipt type="DISABLED" title="CAUTION: Access Denied">
		 You are not allowed to use this tool. <br>
 	 	  If you think this is an error please contact <A HREF="mailto:help@its.utexas.edu"> ITS Help Desk </A> for assistance.
		<%@ include file="commonHidden.jsp"%>
	</bbNG:receipt>
</bbNG:genericPage>