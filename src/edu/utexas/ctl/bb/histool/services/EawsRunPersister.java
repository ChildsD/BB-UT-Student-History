package edu.utexas.ctl.bb.histool.services;

/*
 * Created on Nov 16, 2011 by sp2539
 */
import blackboard.db.*;
import blackboard.platform.intl.*;
import edu.utexas.ctl.bb.histool.entities.BBEawsUser;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class EawsRunPersister {
    
    private ConnectionManager connManager = null;
    private Connection aConnection = null;
    boolean isOracleDatabase = false;
    private StringBuffer queryString;
    PreparedStatement insertQuery = null;
    
    
    /**
     * 
     */
    public EawsRunPersister() {
        super();
        connManager = BbDatabase.getDefaultInstance().getConnectionManager();
        isOracleDatabase = BbDatabase.getDefaultInstance().isOracle();
        
    }

    
    public void insertEAWSRunInfo(EawsCrsLog anEAWSRun, List<BBEawsUser> classList) throws Exception{
        int pk_value = insertEAWSRunnersInfo(anEAWSRun);

        if(pk_value != -1) {
//            Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert of instructor SUCCESS  and now getting ready to insert students pk1=" + pk_value);
            insertEAWSStudentInfoForCourse(classList, pk_value);
            Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert into both tables =SUCCESS");
        }
        else {
            Logger.getLogger(this.getClass()).log(Level.ERROR, "Error storing runs for this course=" + anEAWSRun.getCourse_id());
        }
    }
    

    /*
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
    public void insertEAWSStudentInfoForCourse(List<BBEawsUser> classList, int pk_value) throws Exception {
        queryString = new StringBuffer("");
        try {
            aConnection = connManager.getConnection();
            if(isOracleDatabase) {
                queryString.append("INSERT INTO utxa_eaws_crsuser_status (pk1, eaws_crs_log_pk1, user_id, risklevel, grade, days_since_last_logged_in, notification_type, date_created) ");
                queryString.append("VALUES (utxa_eaws_crsuser_status_seq.nextval,?,?,?,?,?,?,SYSDATE) "); //6
                insertQuery = aConnection.prepareStatement(queryString.toString());
                
                for (Iterator iterator = classList.iterator(); iterator.hasNext();) {
                    BBEawsUser bbEawsUser = (BBEawsUser) iterator.next();
                    insertQuery.setInt(1, pk_value);
                    insertQuery.setString(2, bbEawsUser.getEid());
                    insertQuery.setInt(3, Integer.parseInt(bbEawsUser.getAggregateRiskStatus()));
                    insertQuery.setString(4, bbEawsUser.getCurrentGrade());
                    insertQuery.setInt(5, bbEawsUser.getDaysSinceCrsLastAccessed());
                    insertQuery.setString(6, bbEawsUser.isToNotify() ? null: "E");
                    insertQuery.addBatch();
                }
                
                int [] updateCounts = insertQuery.executeBatch();
                if(updateCounts.length == classList.size()) {
//                    Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert of all class students (" + updateCounts.length + ") eaws status is successful");
                }
                else {
                    Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert of all class students eaws status was NOT successful. total updates = " + updateCounts.length);
                }
            } 
            else {
                Logger.getLogger(this.getClass()).log(Level.ERROR, "Sorry I haven't written the insert method for MS SQL databases yet - awaiting advice/documentation from Blackboard");
            }
        }
        catch(Exception ex) {
            Logger.getLogger(this.getClass()).log(Level.ERROR, "insertEAWSStudentInfoForCourse()  " + ex);
        }
        finally {
            if (insertQuery != null) try { insertQuery.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (aConnection != null) try { aConnection.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
        }
    }
    
    
    // reutrn -1 == failure
    // return a # = pass
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
    
    public int insertEAWSRunnersInfo(EawsCrsLog anEAWSRun) throws Exception {
        queryString = new StringBuffer("");
        ResultSet rowCreated = null;
        int pk_created = -1;
        try {
            aConnection = connManager.getConnection();
            if(isOracleDatabase) {
//                Logger.getLogger(this.getClass()).log(Level.ERROR, "Preparing update statement");
                queryString.append("INSERT INTO utxa_eaws_crs_log (pk1, course_id, user_id, gb_title, risklevel_selected, criteria_selected, feedback, total_notified, date_created) VALUES (utxa_eaws_crs_log_seq.nextval,?,?,?,?,?,?,?,SYSDATE)"); // 6/8
                insertQuery = aConnection.prepareStatement(queryString.toString(),new String[] {"pk1"});
                insertQuery.setString(1, anEAWSRun.getCourse_id());
                insertQuery.setString(2, anEAWSRun.getUser_id());
                insertQuery.setString(3, anEAWSRun.getGb_title());
                insertQuery.setInt(4, anEAWSRun.getRisklevel_selected());
                insertQuery.setString(5, anEAWSRun.getCriteria_selected());
                insertQuery.setString(6, anEAWSRun.getFeedback());
                insertQuery.setInt(7, anEAWSRun.getTotalNotified());
//                Logger.getLogger(this.getClass()).log(Level.ERROR, "update statement PREPARED");
                int insertResult = insertQuery.executeUpdate();

                if(insertResult==1) {
//                 Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert of eaws run SUCCESS");
                    rowCreated = insertQuery.getGeneratedKeys();
                    if (rowCreated.next()) {
                        pk_created = rowCreated.getInt(1);
                    }
                    rowCreated.close();
                }

                insertQuery.close();
                if(insertResult != 1) {
                    // SEJAL: handle error
                    Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert of eaws run FAILED");
                }
                
//                Logger.getLogger(this.getClass()).log(Level.ERROR, "Insert of instructor SUCCESS pk1=" + pk_created);
                return pk_created;
            } 
            else {
                // SEJAL: handle error
                Logger.getLogger(this.getClass()).log(Level.ERROR, "Sorry I haven't written the insert method for MS SQL databases yet - awaiting advice/documentation from Blackboard");
            }
        }
        catch(Exception ex) {
            Logger.getLogger(this.getClass()).log(Level.ERROR, ex);
        }
        finally {
            if (insertQuery != null) try { insertQuery.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (aConnection != null) try { aConnection.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (rowCreated != null) try { rowCreated.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
        }
        
        return -1;
    } //end
    

    public boolean executeSQL(String sqlQuery) throws Exception {
        Statement stmt = null;
        try {
            aConnection = connManager.getConnection();
            if(isOracleDatabase) {
                stmt = aConnection.createStatement();
                stmt.executeUpdate(sqlQuery);
                stmt.close();
            } 
            else {
                Logger.getLogger(this.getClass()).log(Level.ERROR, "Sorry I haven't written the insert method for MS SQL databases yet - awaiting advice/documentation from Blackboard");
            }
            
            return true;
        }
        catch(Exception ex)   {
            Logger.getLogger(this.getClass()).log(Level.ERROR, ex);
        }
        finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
            if (aConnection != null) try { aConnection.close(); } catch (SQLException ex) {Logger.getLogger(this.getClass()).log(Level.ERROR, ex);}
        }
        
        return false;
    } //end
    
    
}
