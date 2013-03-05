package edu.utexas.ctl.bb.histool.services;

/*
 * Created on Nov 16, 2011 by sp2539
 */
import blackboard.db.*;
import edu.utexas.ctl.bb.histool.entities.BBEawsUser;
import edu.utexas.ctl.bb.histool.services.StudentEAWSReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EawsRunLoader {
    
    private ConnectionManager connManager = null;
    private Connection aConnection = null;
    boolean isOracleDatabase = false;
    private StringBuffer selectQueryString;
    PreparedStatement selectQuerypStmt = null;
    
    /**
     * 
     */
    public EawsRunLoader() {
        super();
        connManager = BbDatabase.getDefaultInstance().getConnectionManager();
        isOracleDatabase = BbDatabase.getDefaultInstance().isOracle();
    }
    
    /*
     * 
        <table name="utxa_eaws_crs_log">
                <comment>Table that stores each EAWS report run by the instructor/TA</comment>
                <column name="pk1" data-type="int" nullable="false" identity="true" comment="This is the surrogate primary key for the table." />
                <column name="course_id" data-type="nvarchar(32)" nullable="false" comment="Blackboard course id" />
                <column name="user_id" data-type="nvarchar(32)" nullable="false" comment="EID" />
                <column name="gb_title" data-type="nvarchar(32)" nullable="true" comment="Gradebook column title" />
                <column name="risklevel_selected" data-type="int" nullable="false" comment="1,2,3" />
                <column name="criteria_selected" data-type="nvarchar(32)" nullable="false" comment="G=grade,U=usage, GU=both" />
                <column name="feedback" data-type="nvarchar(500)" nullable="true" comment="Instructor feedback message" />
                <column name="date_created" data-type="datetime" nullable="false" comment="The date and time record created" />
        </table>
     */
    
    public List<EawsCrsLog> selectAllEAWSRunsByUserForCourse(String eid, String crsId) throws Exception {
        selectQueryString = new StringBuffer("");
        selectQueryString.append("SELECT pk1, gb_title, risklevel_selected, criteria_selected, feedback, total_notified, date_created from utxa_eaws_crs_log ");
        selectQueryString.append("WHERE user_id=? and course_id=? order by date_created DESC");
        ResultSet rowsReturned = null;
        EawsCrsLog eawsLog = null;
        List<EawsCrsLog> eawsRuns = new ArrayList<EawsCrsLog>();
        
        try {
            aConnection = connManager.getConnection();
            if(isOracleDatabase) {

                selectQuerypStmt = aConnection.prepareStatement(selectQueryString.toString());
                selectQuerypStmt.setString(1, eid);
                selectQuerypStmt.setString(2, crsId);
                rowsReturned = selectQuerypStmt.executeQuery();
                
                while (rowsReturned.next()) {
                    int pk=rowsReturned.getInt("pk1");
                    String gbTitle = rowsReturned.getString("gb_title");
                    int riskLevel = rowsReturned.getInt("risklevel_selected");
                    String criterionSelected = rowsReturned.getString("criteria_selected");
                    String feedback = rowsReturned.getString("feedback");
                    int total = rowsReturned.getInt("total_notified");
                    Date dateRan = rowsReturned.getDate("date_created");
                    
                    eawsLog = new EawsCrsLog(pk, crsId, eid, riskLevel, criterionSelected, feedback, gbTitle, dateRan, total);
                    eawsRuns.add(eawsLog);
                  }
               
                selectQuerypStmt.close();
//                Logger.getLogger(this.getClass()).log(Level.ERROR, "TOTAL SELECTED = " + eawsRuns.size());
                return eawsRuns.size() > 0 ? eawsRuns: null;
            } 
            else {
                // SEJAL: handle error
                Logger.getLogger(this.getClass()).log(Level.ERROR, "Sorry I haven't written the select method for MS SQL databases yet - awaiting advice/documentation from Blackboard");
            }
        }
        catch(Exception ex) {
            Logger.getLogger(this.getClass()).log(Level.ERROR, ex);
        }
        finally {
            if (selectQuerypStmt != null) try { selectQuerypStmt.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (aConnection != null) try { aConnection.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (rowsReturned != null) try { rowsReturned.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
        }
        
        return null;
    } //end
    
    
    /*
     * <table name="utxa_eaws_crs_log">
                <comment>Table that stores each EAWS report run by the instructor/TA</comment>
                <column name="pk1" data-type="int" nullable="false" identity="true" comment="This is the surrogate primary key for the table." />
                <column name="course_id" data-type="nvarchar(32)" nullable="false" comment="Blackboard course id" />
                <column name="user_id" data-type="nvarchar(32)" nullable="false" comment="EID" />
                <column name="gb_title" data-type="nvarchar(32)" nullable="true" comment="Gradebook column title" />
                <column name="risklevel_selected" data-type="int" nullable="false" comment="1,2,3" />
                <column name="criteria_selected" data-type="nvarchar(32)" nullable="false" comment="G=grade,U=usage, GU=both" />
                <column name="feedback" data-type="nvarchar(500)" nullable="true" comment="Instructor feedback message" />
                <column name="date_created" data-type="datetime" nullable="false" comment="The date and time record created" />
        </table>
        
     * <table name="utxa_eaws_crsusers_status">
            <comment>Table that stores all students in a course and their risk status</comment>
            <column name="pk1" data-type="int" nullable="false" identity="true" comment="This is the surrogate primary key for the table." />
            <column name="eaws_crs_log_pk1" data-type="int" nullable="false" identity="false" comment="This is a foreign key referencing a primary key, pk1 in the custom utxa_eaws_crs_log table" />
            <column name="user_id" data-type="nvarchar(32)" nullable="false" comment="EID of student" />
            <column name="risklevel" data-type="int" nullable="false" comment="1,2,3,4" />
            <column name="grade" data-type="nvarchar(32)" nullable="true" comment="A,B,C,D,F" />
            <column name="days_since_last_logged_in" data-type="int" nullable="true" comment=" any number" />
            <column name="notification_type" data-type="char(1)" nullable="true" comment="E for email" />
            <column name="date_created" data-type="datetime" nullable="false" comment="The date and time record created" />
        </table>
     */
    public List<EawsCrsLog> selectStdEawsStatusForCourse(String eid, String crsId) throws Exception {
        ResultSet rowsReturned = null;
        
        try {
            List<EawsCrsLog> eawsRuns = selectAllEAWSRunsByUserForCourse(eid, crsId);
            
            StringBuffer selectQueryString = new StringBuffer();
            selectQueryString.append("SELECT s.user_id, risklevel, grade, notification_type, l.date_created from utxa_eaws_crsuser_status s, utxa_eaws_crs_log l ");
            selectQueryString.append("WHERE eaws_crs_log_pk1=l.pk1 and course_id=? and l.pk1=?");

            BBEawsUser eawsStudent =null;
            List<BBEawsUser> crsStudentEawsStatusList = null;
            
            aConnection = connManager.getConnection();
            if(isOracleDatabase) {
                selectQuerypStmt = aConnection.prepareStatement(selectQueryString.toString());
                
                for (Iterator iterator = eawsRuns.iterator(); iterator.hasNext();) {
                    EawsCrsLog anEawsRun = (EawsCrsLog) iterator.next();
                    
                    selectQuerypStmt.setString(1, crsId);
                    selectQuerypStmt.setInt(2, anEawsRun.getPk1());
                    
                    rowsReturned = selectQuerypStmt.executeQuery();

                    crsStudentEawsStatusList = new ArrayList<BBEawsUser>();
                    while (rowsReturned.next()) {
                        String studEid = rowsReturned.getString("user_id");
                        int riskLevel = rowsReturned.getInt("risklevel");
                        String notification_type = rowsReturned.getString("notification_type");

                        boolean isNotified = false;
                        if(notification_type != null && notification_type.equalsIgnoreCase("E")) {
                            isNotified = true;
                        }

                        eawsStudent = new BBEawsUser(studEid, "", "", Integer.toString(riskLevel), isNotified);
                        crsStudentEawsStatusList.add(eawsStudent);
                      } //end while
                   
                    anEawsRun.setStudentEawsStatusList(crsStudentEawsStatusList.size() > 0 ? crsStudentEawsStatusList: null);
                }  
                
                selectQuerypStmt.close();
                return eawsRuns.size() > 0 ? eawsRuns: null;
            } 
            else {
                Logger.getLogger(this.getClass()).log(Level.ERROR, "Sorry I haven't written the select method for MS SQL databases yet - awaiting advice/documentation from Blackboard");
            }
        }
        catch(Exception ex) {
            Logger.getLogger(this.getClass()).log(Level.ERROR, ex);
        }
        finally {
            if (selectQuerypStmt != null) try { selectQuerypStmt.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (aConnection != null) try { aConnection.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (rowsReturned != null) try { rowsReturned.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
        }
        
        return null;
    } //end
    

/*
 * select utxa_eaws_crsuser_status.user_id, risklevel, eaws_crs_log_pk1, utxa_eaws_crsuser_status.date_created from bblearn.utxa_eaws_crsuser_status, bblearn.utxa_eaws_crs_log 
where utxa_eaws_crsuser_status.user_id='portala'  
    and course_id='2011_fall_12345_MAN_999F' 
    and eaws_crs_log_pk1= utxa_eaws_crs_log.pk1;
 */
    public  StudentEAWSReport getLatestStudentEawsStatusForCrs(String crsId, String eid) throws Exception {
        StringBuffer selectQueryString = new StringBuffer();
        selectQueryString.append("select l.user_id, feedback, risklevel, s.date_created from utxa_eaws_crsuser_status s, utxa_eaws_crs_log l ");
        selectQueryString.append("WHERE s.user_id=?  and course_id=? and  eaws_crs_log_pk1=l.pk1  and s.notification_type='E' order by s.date_created DESC" );
        ResultSet rowsReturned = null;
        StudentEAWSReport studentReport = null;
        
        try {
            aConnection = connManager.getConnection();
            if(isOracleDatabase) {
  
                selectQuerypStmt = aConnection.prepareStatement(selectQueryString.toString());
                selectQuerypStmt.setString(1, eid);
                selectQuerypStmt.setString(2, crsId);
                rowsReturned = selectQuerypStmt.executeQuery();

                while (rowsReturned.next()) {
                    studentReport = new StudentEAWSReport();
                    studentReport.setInstructorFeedback(rowsReturned.getString("feedback"));
                    studentReport.setRiskLevel(String.valueOf(rowsReturned.getInt("risklevel")));
                    studentReport.setInstructorEid(rowsReturned.getString("user_id"));
                    studentReport.setDateReported(rowsReturned.getDate("date_created"));
                    
//                    Logger.getLogger(this.getClass()).log(Level.ERROR, "SUCCESS got latest student info");
                    
                    break;  //we only need first row since we are returning order by date
                  }
               
                selectQuerypStmt.close();
                return studentReport;
            } 
            else {
                Logger.getLogger(this.getClass()).log(Level.ERROR, "Sorry I haven't written the select method for MS SQL databases yet - awaiting advice/documentation from Blackboard");
            }
        }
        catch(Exception ex) {
            Logger.getLogger(this.getClass()).log(Level.ERROR, ex);
        }
        finally {
            if (selectQuerypStmt != null) try { selectQuerypStmt.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (aConnection != null) try { aConnection.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (rowsReturned != null) try { rowsReturned.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
        }
        
        return null;
    } //end
}
