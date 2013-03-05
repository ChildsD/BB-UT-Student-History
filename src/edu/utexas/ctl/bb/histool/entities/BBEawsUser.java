package edu.utexas.ctl.bb.histool.entities;

/*
 * Created on May 25, 2010 by sp2539
 */
public class BBEawsUser implements  java.io.Serializable {

    private String eid;
    private String uin;
    private boolean toNotify;


    private String firstName;
    private String lastName;
    private int status;
    private String currentGrade; // A,B,C,D,F
    private String aggregateRiskStatus; //1,2,3
    

    private int daysSinceCrsLastAccessed; // #
    private String email; // email address
    private String GRADE_RISK="0"; // 0,1,2,3
    private boolean LMS_USAGE_RISK=false;
    private boolean ATTENDANCE_RISK=false;
    private String noteType; // based on advisor's toolkit

    public BBEawsUser(String eid, String firstName, String lastName, String riskStatus, boolean isEmailed) {
        super();
        this.eid = eid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aggregateRiskStatus = riskStatus;
        this.toNotify = isEmailed;
    }
    
    /**
     * @param eid
     * @param firstName
     * @param lastName
     */
    public BBEawsUser(String theUin, String eid, String firstName, String lastName, String theEmail) {
        super();
        this.eid = eid;
        this.uin = theUin;
        this.firstName = firstName;
        this.lastName = lastName;
        email = theEmail;
    }
    

    /**
     * @return the toNotify
     */
    public boolean isToNotify() {
        return toNotify;
    }

    /**
     * @param toNotify the toNotify to set
     */
    public void setToNotify(boolean toNotify) {
        this.toNotify = toNotify;
    }
    
    
    /**
     * @return the aTTENDANCE_RISK
     */
    public boolean isATTENDANCE_RISK() {
        return ATTENDANCE_RISK;
    }

    /**
     * @param aTTENDANCERISK the aTTENDANCE_RISK to set
     */
    public void setATTENDANCE_RISK(boolean aTTENDANCERISK) {
        ATTENDANCE_RISK = aTTENDANCERISK;
    }
    public  BBEawsUser cloneThisUser() {
        
        BBEawsUser aUser = new BBEawsUser();
        aUser.setAggregateRiskStatus(aggregateRiskStatus);
        aUser.setCurrentGrade(currentGrade);
        aUser.setDaysSinceCrsLastAccessed(daysSinceCrsLastAccessed);
        aUser.setEid(eid);
        aUser.setEmail(email);
        aUser.setToNotify(toNotify);
        aUser.setFirstName(firstName);
        aUser.setGRADE_RISK(GRADE_RISK);
        aUser.setLastName(lastName);
        aUser.setLMS_USAGE_RISK(LMS_USAGE_RISK);
        aUser.setNoteType(noteType);
        aUser.setStatus(status);
        aUser.setUin(uin);
        aUser.setATTENDANCE_RISK(ATTENDANCE_RISK);
       
        return aUser;
    }
    /**
     * @param eid
     * @param uin
     * @param status
     * @param aggregateRiskStatus
     * @param noteType
     */
    public BBEawsUser(String eid, String uin, String aggregateRiskStatus, String noteType) {
        super();
        this.eid = eid;
        this.uin = uin;
        this.aggregateRiskStatus = aggregateRiskStatus;
        this.noteType = noteType;
    }
    

    /**
     * @return Returns the uin.
     */
    public String getUin() {
        return uin;
    }
    /**
     * @param uin The uin to set.
     */
    public void setUin(String uin) {
        this.uin = uin;
    }
    
    /**
     * @return Returns the noteType.
     */
    public String getNoteType() {
        return noteType;
    }
    /**
     * @param noteType The noteType to set.
     */
    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    /**
     * @return Returns the aggregateRiskStatus.
     */
    public String getAggregateRiskStatus() {
        return aggregateRiskStatus;
    }

    /**
     * @param aggregateRiskStatus The aggregateRiskStatus to set.
     */
    public void setAggregateRiskStatus(String aggregateRiskStatus) {
        this.aggregateRiskStatus = aggregateRiskStatus;
    }

    /**
     * @return Returns the gRADE_RISK.
     */
    public String getGRADE_RISK() {
        return GRADE_RISK;
    }

    /**
     * @param gRADERISK The gRADE_RISK to set.
     */
    public void setGRADE_RISK(String risk) {
        GRADE_RISK = risk;
    }

    /**
     * @return Returns the lMS_USAGE_RISK.
     */
    public boolean isLMS_USAGE_RISK() {
        return LMS_USAGE_RISK;
    }

    /**
     * @param lMSUSAGERISK The lMS_USAGE_RISK to set.
     */
    public void setLMS_USAGE_RISK(boolean lMSUSAGERISK) {
        LMS_USAGE_RISK = lMSUSAGERISK;
    }

    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Returns the daysSinceCrsLastAccessed.
     */
    public int getDaysSinceCrsLastAccessed() {
        return daysSinceCrsLastAccessed;
    }

    /**
     * @param daysSinceCrsLastAccessed The daysSinceCrsLastAccessed to set.
     */
    public void setDaysSinceCrsLastAccessed(int daysSinceCrsLastAccessed) {
        this.daysSinceCrsLastAccessed = daysSinceCrsLastAccessed;
    }

    /**
     * @return Returns the currentGrade.
     */
    public String getCurrentGrade() {
        return currentGrade;
    }

    /**
     * @param currentGrade The currentGrade to set.
     */
    public void setCurrentGrade(String currentGrade) {
        this.currentGrade = currentGrade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BBEawsUser() {
//        score="";
        currentGrade="";
        aggregateRiskStatus="0";
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
}
