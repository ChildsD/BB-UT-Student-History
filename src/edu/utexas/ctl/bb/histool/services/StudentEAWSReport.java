package edu.utexas.ctl.bb.histool.services;

/*
 * Created on Aug 27, 2009 by sp2539
 */

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.utexas.ctl.bb.histool.services.BlackboardService;
import edu.utexas.ctl.bb.histool.services.EAWSConstants;
import blackboard.data.content.StaffInfo;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.Id;
import blackboard.platform.gradebook2.BookData;
import blackboard.platform.gradebook2.BookDataRequest;
import blackboard.platform.gradebook2.GradableItem;
import blackboard.platform.gradebook2.GradebookManager;
import blackboard.platform.gradebook2.GradebookManagerFactory;


public class StudentEAWSReport {

        private String officeHours;
        private String officeLocation;
        private String instructorFeedback;
        private String riskLevel;
        private String taOfficeHrsLocation;
        private String instructorEid;
        private String eawsRiskComment;
        private String instructorEmail;
        private Date dateReported;
        private String dateReportedString;
        private String instructorName;
        /**
         * @return the dateReportedString
         */
        public String getDateReportedString() {
            return dateReportedString;
        }

        /**
         * @param dateReportedString the dateReportedString to set
         */
        public void setDateReportedString(String dateReportedString) {
            this.dateReportedString = dateReportedString;
        }

        /**
         * @return the dateReported
         */
        public Date getDateReported() {
            return dateReported;
        }

        /**
         * @param dateReported the dateReported to set
         */
        public void setDateReported(Date dateReported) {
            this.dateReported = dateReported;
            dateReportedString = dateReported.toString();
        }

        /**
         * @return the instructorEmail
         */
        public String getInstructorEmail() {
            return instructorEmail;
        }
        

        public String getInstructorEmailLink() {
            return "<a href=\"mailto:" + instructorEmail + "\">" + instructorName + " </a>";
        }

        /**
         * @param instructorEmail the instructorEmail to set
         */
        public void setInstructorEmail(String instructorEmail) {
            this.instructorEmail = instructorEmail;
        }

        /**
         * @return the eawsRiskComment
         */
        public String getEawsRiskComment() {
            return eawsRiskComment;
        }

        /**
         * @param eawsRiskComment the eawsRiskComment to set
         */
        public void setEawsRiskComment(String eawsRiskComment) {
            this.eawsRiskComment = eawsRiskComment;
        }

        /**
         * @return the instructorEid
         */
        public String getInstructorEid() {
            return instructorEid;
        }

        /**
         * @param instructorEid the instructorEid to set
         */
        public void setInstructorEid(String instructorEid) {
            this.instructorEid = instructorEid;
        }
        BlackboardService bbService = null;
        
        
        public StudentEAWSReport() {             
           
        }
        
        public void createStudentReport(Id crsId) {
            
            try {
                bbService = new BlackboardService();
                User instructor = bbService.getUser(instructorEid);
                instructorName= instructor.getGivenName() + " " + instructor.getFamilyName();
                StaffInfo facInfo = bbService.getFacultyInfo(instructor);
                officeLocation = facInfo.getOfficeAddress();
                officeHours =facInfo.getOfficeHours();
                instructorEmail = instructor.getEmailAddress();
                EAWSConstants constants = new EAWSConstants();
                
                eawsRiskComment = constants.generateEAWSComments(Integer.parseInt(riskLevel));
            } catch (Exception ex) {
                Logger.getLogger(this.getClass()).log(Level.ERROR, null, ex);
            }
                
        }
        
        
        
        /**
         * @return the instructorFeedback
         */
        public String getInstructorFeedback() {
            return instructorFeedback;
        }
        /**
         * @param instructorFeedback the instructorFeedback to set
         */
        public void setInstructorFeedback(String instructorFeedback) {
            this.instructorFeedback = instructorFeedback;
        }
        /**
         * @return the riskLevel
         */
        public String getRiskLevel() {
            return riskLevel;
        }
        /**
         * @param riskLevel the riskLevel to set
         */
        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        /**
         * @return Returns the taOfficeHrsLocation.
         */
        public String getTaOfficeHrsLocation() {
            return taOfficeHrsLocation;
        }
        /**
         * @param taOfficeHrsLocation The taOfficeHrsLocation to set.
         */
        public void setTaOfficeHrsLocation(String taOfficeHrsLocation) {
            this.taOfficeHrsLocation = taOfficeHrsLocation;
        }

             /**
         * @return Returns the officeHours.
         */
        public String getOfficeHours() {
            return officeHours;
        }
        
        /**
         * @param officeHours The officeHours to set.
         */
        public void setOfficeHours(String officeHours) {
            this.officeHours = officeHours;
        }
        /**
         * @return Returns the officeLocation.
         */
        public String getOfficeLocation() {
            return officeLocation;
        }
        /**
         * @param officeLocation The officeLocation to set.
         */
        public void setOfficeLocation(String officeLocation) {
            this.officeLocation = officeLocation;
        }
        /**
         * @return Returns the otherResources.
         */
 
}
