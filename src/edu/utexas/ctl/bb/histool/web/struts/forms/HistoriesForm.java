package edu.utexas.ctl.bb.histool.web.struts.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.jCharts.axisChart.AxisChart;

import blackboard.data.user.User;
import blackboard.persist.Id;

import edu.utexas.ctl.bb.histool.entities.BBUser;
import edu.utexas.ctl.bb.histool.entities.BBUserHistory;
import edu.utexas.ctl.bb.histool.services.BlackboardService;

public class HistoriesForm extends ActionForm {
	
	private ArrayList<BBUserHistory> histories;
	private BBUserHistory single_history;
	private ArrayList<User> enrollment;
	private String eid;
	private boolean valid_eid;
	private double GPA;
	private String sort;
	private AxisChart axisChart;
	
	public ArrayList<BBUserHistory> getHistories()
	{
		return this.histories;
	}
	
	public void setHistories(ArrayList<BBUserHistory> histories)
	{
		this.histories=histories;
	}
	
	public List<User> getEnrollment()
	{
		return this.enrollment;
	}
	
	public void setEnrollment(ArrayList<User> enrollment)
	{
		this.enrollment = enrollment;
	}
	
	public String getEid()
	{
		return this.eid;
	}
	
	public void setEid(String eid)
	{
		this.eid=eid;
	}
	
	public HistoriesForm()
	{
		this.eid = "";
	}

	public boolean isValid_eid() {
		return valid_eid;
	}

	public void setValid_eid(boolean valid_eid) {
		this.valid_eid = valid_eid;
	}

	public BBUserHistory getSingle_history() {
		return single_history;
	}

	public void setSingle_history(BBUserHistory single_history) {
		this.single_history = single_history;
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(double gPA) {
		GPA = gPA;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public AxisChart getAxisChart() {
		return axisChart;
	}

	public void setAxisChart(AxisChart axisChart) {
		this.axisChart = axisChart;
	}
}
