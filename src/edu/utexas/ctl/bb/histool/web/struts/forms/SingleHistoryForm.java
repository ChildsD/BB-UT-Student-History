package edu.utexas.ctl.bb.histool.web.struts.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts.action.ActionForm;

import blackboard.data.user.User;
import blackboard.persist.Id;

import edu.utexas.ctl.bb.histool.entities.BBUser;
import edu.utexas.ctl.bb.histool.entities.BBUserHistory;
import edu.utexas.ctl.bb.histool.services.BlackboardService;

public class SingleHistoryForm extends ActionForm{
	private BBUserHistory history;
	private String eid;

	public BBUserHistory getHistory() {
		return history;
	}

	public void setHistory(BBUserHistory history) {
		this.history = history;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
}