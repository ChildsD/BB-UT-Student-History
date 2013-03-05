//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package edu.utexas.ctl.bb.histool.web.struts.actions;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.text.DecimalFormat;
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
import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.LineChartProperties;
import org.jCharts.properties.PointChartProperties;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;

import edu.utexas.ctl.bb.histool.entities.BBUserHistory;
import edu.utexas.ctl.bb.histool.services.BlackboardService;
import edu.utexas.ctl.bb.histool.services.GPAComparator;
import edu.utexas.ctl.bb.histool.services.LastNameComparator;
import edu.utexas.ctl.bb.histool.services.SATComparator;
import edu.utexas.ctl.bb.histool.web.struts.forms.HistoriesForm;
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

public class ShowStudentsHistoriesAction extends BaseAction {

	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		HistoriesForm hForm = (HistoriesForm) form;

		Id crsId = (Id) getSessionObj(request, "courseId");
		BlackboardService bbService = new BlackboardService();
		List enrollmentList = bbService.getAllNonInstrsEnrolledInCrs(crsId);
		setRequestObj(request, "theEnrollmentList", enrollmentList);
		ArrayList<BBUserHistory> historyList = new ArrayList<BBUserHistory>();
		BbSessionManagerService sessionService =  BbSessionManagerServiceFactory.getInstance();
		BbSession bbSession = sessionService.getSession(request);

		Id userId = bbSession.getUserId();

		try {
			CourseMembership cmUser = bbService.getCourseMembershipForUser(crsId, userId);
			if(cmUser==null) {
				return mapping.findForward("noAccess_jsp");
			}



			if(request.getParameter("populate") != null) {
				//BEGIN NEW STUFF
				BlackboardService bbsObj = new BlackboardService();
				User usrObj = bbsObj.getUser(hForm.getEid());

				if(request.getParameter("populate") == "2")
				{
					usrObj = bbsObj.getUser(request.getParameter("eid"));
					if(usrObj == null)
						return mapping.findForward("noAccess_jsp");

					if(usrObj.getFamilyName() == "Smith")
						return mapping.findForward("contact_jsp");

					hForm.setValid_eid(true);	
					BBUserHistory temp = new BBUserHistory(usrObj, crsId.toString());
					hForm.setSingle_history(temp);
					request.setAttribute("history", temp);
					return mapping.findForward("contact_jsp");
				}

				else
				{
					hForm.setValid_eid(true);	
					BBUserHistory temp = new BBUserHistory(usrObj, crsId.toString());
					historyList.add(temp);
					hForm.setHistories(historyList);
					return mapping.findForward("showStudentsHistories_jsp");
				}

			}

			else if(hForm.getSort() != null)
			{
				historyList = hForm.getHistories();
				if(hForm.getSort().equals("Name"))
					Collections.sort(historyList, new LastNameComparator());
				else if(hForm.getSort().equals("GPA"))
					Collections.sort(historyList, new GPAComparator());
				else if(hForm.getSort().equals("SAT"))
					Collections.sort(historyList, new SATComparator());
				hForm.setHistories(historyList);
				hForm.setSort("");
				return mapping.findForward("showStudentsHistories_jsp");
			}

			//This case SHOULD only be reached once: upon the initial load.
			//It calculates and stores the course roster as well as the BBUserHistory objects for each student
			else
			{
				
				CourseMembershipDbLoader loader = CourseMembershipDbLoader.Default.getInstance(); //Create loader object
				BbList<CourseMembership> enrollment = loader.loadByCourseId(crsId, null, true);
				Iterator<CourseMembership> itObjects = enrollment.iterator();
				double sum = 0;
				int count = 0;
				List<Double> gpaList = new ArrayList<Double>();

				while(itObjects.hasNext())
				{
					CourseMembership tempMember = itObjects.next();
					User tempUser = tempMember.getUser();
					BBUserHistory tempHistory = new BBUserHistory(tempUser, crsId.toString());
					if(tempMember.getRoleAsString().toUpperCase().equals("STUDENT"))
					{
						historyList.add(tempHistory);
						double temp = tempHistory.getGPA();
						sum += temp;
						count++;
					}

				}

				hForm.setHistories(historyList);
				hForm.setGPA(roundTwoDecimals(sum/count));
//				hForm.setAxisChart(createChart(gpaList, count));
				return mapping.findForward("showStudentsHistories_jsp");
			}

		} catch (Exception ex) {
			Logger.getLogger(this.getClass()).log(Level.ERROR, null, ex);
			return mapping.findForward("noAccess_jsp");
		}

		//        hForm.reset(mapping, request);
		//        hForm.setEid("");
		//        List<BBUserHistory> newHistories = new ArrayList<BBUserHistory>();
		//        hForm.setUserHistories(newHistories);
		//        return mapping.findForward("showStudentsClasses_jsp");
	}

	double roundTwoDecimals(double d) {
		DecimalFormat threeDForm = new DecimalFormat("#.###");
		return Double.valueOf(threeDForm.format(d));
	}
	
	AxisChart createChart(List<Double> gpaList, int listSize) throws ChartDataException
	{
		//NOTE: This method is based on the example found locally at: Downloads/jCharts-0.7.0/docs/userGuide0.3.0/lineChart.html
		String[] xAxisLabels= new String[listSize];//I don't want x labels right now, so I made the array full of null values. Can I do that?
		String xAxisTitle= "";
		String yAxisTitle= "GPA";
		String title= "Class GPAs";
		DataSeries dataSeries = new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle, title );
		
		double[][] data = new double[1][listSize];
		Iterator it = gpaList.iterator();
		for(int i = 0; i < listSize; i++)
		{
			data[0][i] = (Double) it.next();
		}
		String[] legendLabels= { "GPA" };
		Paint[] paints= TestDataGenerator.getRandomPaints( 1 );
		
		Stroke[] strokes= { LineChartProperties.DEFAULT_LINE_STROKE };
		Shape[] shapes= { PointChartProperties.SHAPE_CIRCLE };
		LineChartProperties lineChartProperties= new LineChartProperties( strokes, shapes );
		
		AxisChartDataSet axisChartDataSet= new AxisChartDataSet( data, legendLabels, paints, ChartType.LINE, lineChartProperties );
		dataSeries.addIAxisPlotDataSet( axisChartDataSet );

		ChartProperties chartProperties= new ChartProperties();
		AxisProperties axisProperties= new AxisProperties();
		LegendProperties legendProperties= new LegendProperties();

		AxisChart axisChart= new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 50, 50); //I changed the last 2 parameters to get rid of errors
		
		return axisChart;
	}

}