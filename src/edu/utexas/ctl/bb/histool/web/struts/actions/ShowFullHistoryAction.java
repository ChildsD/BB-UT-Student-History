//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package edu.utexas.ctl.bb.histool.web.struts.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.utexas.ctl.bb.histool.entities.BBUserHistory;
import edu.utexas.ctl.bb.histool.services.BlackboardService;
import edu.utexas.ctl.bb.histool.web.struts.forms.HistoriesForm;
import edu.utexas.ctl.bb.histool.web.struts.forms.SingleHistoryForm;
import edu.utexas.diia.bb.gbtool.web.struts.actions.BaseAction;

import blackboard.base.BbList;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.gradebook.Lineitem;
import blackboard.data.user.User;
import blackboard.persist.Id;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseSearch;
import blackboard.platform.security.NonceUtil;
import blackboard.platform.session.BbSession;
import blackboard.platform.session.BbSessionManagerService;
import blackboard.platform.session.BbSessionManagerServiceFactory;

public class ShowFullHistoryAction extends BaseAction {

	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		SingleHistoryForm shForm = (SingleHistoryForm) form;

		Id crsId = (Id) getSessionObj(request, "courseId");
		String populate = request.getParameter("populate");
		BlackboardService bbService = new BlackboardService();
		//        List enrollmentList = bbService.getAllNonInstrsEnrolledInCrs(crsId);
		//        setRequestObj(request, "theEnrollmentList", enrollmentList);
		User usrObj;
		BBUserHistory history;
		BbSessionManagerService sessionService =  BbSessionManagerServiceFactory.getInstance();
		BbSession bbSession = sessionService.getSession(request);
		Id userId = bbSession.getUserId();

		try {
			CourseMembership cmUser = bbService.getCourseMembershipForUser(crsId, userId);
			if(cmUser==null) {
				return mapping.findForward("noAccess_jsp");
			}
			
			if(populate.equals("1"))
			{
				usrObj = bbService.getUser(shForm.getEid());
				history = new BBUserHistory(usrObj, crsId.toString());
				shForm.setHistory(history);
				shForm.setEid("");
				return mapping.findForward("showFullHistory_jsp");
			}

			else if(populate.equals("2")) {
				//BEGIN NEW STUFF
				String eid = request.getParameter("eid");
				usrObj = bbService.getUser(eid);
				history = new BBUserHistory(usrObj, crsId.toString());
				BlackboardService bbsObj = new BlackboardService();
//				User usrObj = bbsObj.getUser(hForm.getEid());

//				usrObj = bbsObj.getUser(request.getParameter("eid"));

				if(usrObj.getFamilyName() == "Smith")
					return mapping.findForward("contact_jsp");

				shForm.setHistory(history);
				return mapping.findForward("showFullHistory_jsp");
			}
			
			else
				return mapping.findForward("showStudentsHistories_jsp");
		} catch (Exception ex) {
			Logger.getLogger(this.getClass()).log(Level.ERROR, null, ex);
			return mapping.findForward("contact_jsp");
		}
	}    
}