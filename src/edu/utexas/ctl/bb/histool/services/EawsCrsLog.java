package edu.utexas.ctl.bb.histool.services;

/*
 * Created on Nov 16, 2011 by sp2539
 */

import edu.utexas.ctl.bb.histool.entities.BBEawsUser;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/* 
<table name="utxa_eaws_crs_log">
        <comment>Table that stores each EAWS report run by the instructor/TA</comment>
        <column name="pk1" data-type="int" nullable="false" identity="true" comment="This is the surrogate primary key for the table." />
        <column name="course_id" data-type="nvarchar(32)" nullable="false" comment="Blackboard course id" />
        <column name="user_id" data-type="nvarchar(32)" nullable="false" comment="EID" />
        <column name="gb_title" data-type="nvarchar(32)" nullable="true" comment="Gradebook column title" />
        <column name="risklevel_selected" data-type="int" nullable="false" comment="1,2,3" />
        <column name="criteria_selected" data-type="nvarchar(32)" nullable="false" comment="G=grade,U=usage, GU=both" />
        <column name="feedback" data-type="nvarchar(500)" nullable="true" comment="Instructor feedback message" />
        <column name="dateRan" data-type="datetime" nullable="false" comment="The date and time record created" />
</table>
*/


public class EawsCrsLog {
    
    private int pk1;
    private String course_id;
    private String user_id;
    private String gb_title;
    private int risklevel_selected;
    private String criteria_selected;
    private String feedback;
    private Date dateRan;
    private List<BBEawsUser> studentEawsStatusList;
    int totalNotified;
   

    /**
     * @return the totalNotified
     */
    public int getTotalNotified() {
        return totalNotified;
    }


    /**
     * @param totalNotified the totalNotified to set
     */
    public void setTotalNotified(int totalNotified) {
        this.totalNotified = totalNotified;
    }


    /**
     * @return the dateRanFormatted
     */
    public String getDateRanFormatted() {
        
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateRan);
    }


    /**
     * @return the dateRan
     */
    public Date getDateRan() {
        return dateRan;
    }

    /**
     * @param dateRan the dateRan to set
     */
    public void setDateRan(Date dateRan) {
        this.dateRan = dateRan;
    }

    /**
     * @return the studentEawsStatusList
     */
    public List<BBEawsUser> getStudentEawsStatusList() {
        return studentEawsStatusList;
    }

    /**
     * @param studentEawsStatusList the studentEawsStatusList to set
     */
    public void setStudentEawsStatusList(List<BBEawsUser> studentEawsStatusList) {
        this.studentEawsStatusList = studentEawsStatusList;
    }

    /**
     * @param courseId
     * @param userId
     * @param risklevelSelected
     * @param criteriaSelected
     * @param feedback
     */
    public EawsCrsLog(String courseId, String userId, int risklevelSelected, String criteriaSelected, String feedback, String gbTitle, int totalNotified) {
        super();
        course_id = courseId;
        user_id = userId;
        gb_title = gbTitle;
        risklevel_selected = risklevelSelected;
        criteria_selected = criteriaSelected;
        this.feedback = feedback;
        this.totalNotified = totalNotified;
    }
    
    public EawsCrsLog(int pk, String courseId, String userId, int risklevelSelected, String criteriaSelected, String feedback, String gbTitle, Date aDate, int totalNotified) {
        super();
        pk1 = pk;
        course_id = courseId;
        user_id = userId;
        gb_title = gbTitle;
        risklevel_selected = risklevelSelected;
        criteria_selected = criteriaSelected;
        this.feedback = feedback;
        dateRan = aDate;
        this.totalNotified = totalNotified;
    }
    public EawsCrsLog() {
        
    }
    
    /**
     * @return the gb_title
     */
    public String getGb_title() {
        return gb_title;
    }


    /**
     * @param gbTitle the gb_title to set
     */
    public void setGb_title(String gbTitle) {
        gb_title = gbTitle;
    }
    

    /**
     * @return the pk1
     */
    public int getPk1() {
        return pk1;
    }

    /**
     * @param pk1 the pk1 to set
     */
    public void setPk1(int pk1) {
        this.pk1 = pk1;
    }

    /**
     * @return the course_id
     */
    public String getCourse_id() {
        return course_id;
    }
    /**
     * @param courseId the course_id to set
     */
    public void setCourse_id(String courseId) {
        course_id = courseId;
    }
    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }
    /**
     * @param userId the user_id to set
     */
    public void setUser_id(String userId) {
        user_id = userId;
    }

    /**
     * @return the risklevel_selected
     */
    public int getRisklevel_selected() {
        return risklevel_selected;
    }

    /**
     * @param risklevelSelected the risklevel_selected to set
     */
    public void setRisklevel_selected(int risklevelSelected) {
        risklevel_selected = risklevelSelected;
    }

    /**
     * @return the criteria_selected
     */
    public String getCriteria_selected() {
        return criteria_selected;
    }
    /**
     * @param criteriaSelected the criteria_selected to set
     */
    public void setCriteria_selected(String criteriaSelected) {
        criteria_selected = criteriaSelected;
    }
    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }
    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    
}
