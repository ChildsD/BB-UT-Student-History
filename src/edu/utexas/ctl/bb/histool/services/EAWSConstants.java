package edu.utexas.ctl.bb.histool.services;

public class EAWSConstants {
    
        public static final int HIGH=1;
        public static final int MED=2;
        public static final int LOW=3;
        
        public static final String HIGH_RISK="1";
        public static final String MED_RISK="2";
        public static final String LOW_RISK="3";
        public static final String UNHANDLED_RISK="4";
        
        
//        public static final String HIGH_RISK_EMAIL_TXT="I am sending this out to make you aware of your low class grades in Blackboard. Please review lectures 1 and 2 that I have uploaded to Blackboard. In order to be successful in this course, please use the material that I have uploaded for our upcoming lectures.";
        public static final String HIGH_RISK_EMAIL_TXT="I'm sending this email because you are earning low grades thus far in the course.  If you check the gradebook, you'll notice that I've entered grades for Assignments 1 and 2.  To be a successful student, please read the material I'm posting to Blackboard. You have an opportunity to improve on your grade! If you have any questions come visit me in my office.";
//        public static final String MED_RISK_EMAIL_TXT="I am sure you are aware of your grades in Blackboard for this class. I do not want you to fall behind. I would like you to review Exam 1 and assignments 2 and 3.";
        public static final String MED_RISK_EMAIL_TXT="I'm sending this email because I have reviewed your grades and noticed that there is room for you to improve your performance.  To be more successful you can 1) review the chapter handouts that will help focus your readings covered on each assignment, 2) review the class presentation materials I am posting on Blackboard, 3) make sure you are checking in with your other team members, Please check the gradebook and try not to fall behind on future assignments.";
        public static final String NO_RISK_EMAIL_TXT="I have noted that you are performing well, keep up the good work!";
        
        public static final String RED_GRADE_RISK="I am concerned about your <font COLOR=\"#984807\" ><b>grade</b> </font> in this course.";
        public static final String YELLOW_GRADE_RISK="I am concerned: I want to be sure you are aware of your grades in Blackboard";
        public static final String NO_GRADE_RISK="No academic concerns are reported.";
        
//        public static final String LMS_USAGE_RISK="I see you have not accessed this course in Blackboard lately";
        public static final String LMS_USAGE_RISK="I am concerned: that you haven't accessed the course materials on Blackboard recently.";
        public static final String ATTENDANCE_RISK="I am concerned about your <font COLOR=\"#984807\" ><b>poor attendance </b></font> in class.";
        
        
        // Advisor's toolKit constants
        // risk status
        public static final String RED="R";
        public static final String YELLOW="Y";
        public static final String GREEN="G";
        
        // note type
        public static final String ATTENDANCE="A";
        public static final String GRADE="G";
        public static final String USAGELMS="U";
        public static final String ATTENDANCE_GRADE="1";
        public static final String ATTENDANCE_USAGE="2";
        public static final String GRADE_USAGE="3";
        public static final String ATTENDANCE_GRADE_USAGE="9";
        
//        public static final String AG="1";
//        public static final String AU="2";
//        public static final String GU="3";
//        public static final String AGU="9";
        
        public static final String CCYYS="20109";
        
        public String getEAWSTitle(int risk_level) {
            switch (risk_level) {
            case EAWSConstants.HIGH:
                return "High Risk(Red)";
            case EAWSConstants.MED:
                return "Medium Risk(Yellow)";
            case EAWSConstants.LOW:
                return "Low Risk(Green)";
            default:
                return "";
            }
        }
        
        public String generateEAWSComments(int risk_level) {
            switch (risk_level) {
            case HIGH:
                return RED_GRADE_RISK;
            case MED:
                return YELLOW_GRADE_RISK;
            case LOW:
                return NO_GRADE_RISK;
            default:
                return "";
            }
        }
        
        public String replace(String str, String pattern, String replace) {
            int s = 0;
            int e = 0;
            StringBuffer result = new StringBuffer();

            while ((e = str.indexOf(pattern, s)) >= 0) {
                result.append(str.substring(s, e));
                result.append(replace);
                s = e+pattern.length();
            }
            result.append(str.substring(s));
            return result.toString();
        }
        
        public static String getRiskStatus(String riskStatus) {
            
            int risk_status = Integer.parseInt(riskStatus);
            
            if(risk_status==EAWSConstants.HIGH) return EAWSConstants.RED;
            if(risk_status==EAWSConstants.MED) return EAWSConstants.YELLOW;
            if(risk_status==EAWSConstants.LOW) return EAWSConstants.GREEN;
            
            return null;
        }

        
}
