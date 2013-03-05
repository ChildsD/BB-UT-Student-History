package edu.utexas.ctl.bb.histool.entities;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.utexas.ctl.bb.histool.services.EawsRunLoader;
import edu.utexas.ctl.bb.histool.services.StudentEAWSReport;
import blackboard.data.user.User;
import blackboard.persist.Id;

import java.text.DecimalFormat;
import java.util.Random;

public class BBUserHistory {

	private String eid;
	private String fullName;
	private String classification;
	private User student;
	private String photoLink;
	private String major;
	private double GPA;
	private int readingSAT;
	private int mathSAT;
	private int writingSAT;
	private String lastName;
	private StudentEAWSReport eawsReport;
	private String eawsRiskLevel;

	public BBUserHistory(User student, String crsId)
	{
		Random generator = new Random();
		String eid = student.getUserName();
		this.student=student;
		this.eid=eid;
		this.lastName = student.getFamilyName();
		this.fullName=student.getGivenName().concat(" ").concat(student.getFamilyName());
		
		EawsRunLoader loader = new EawsRunLoader();
		try {
			StudentEAWSReport report = loader.getLatestStudentEawsStatusForCrs(crsId, eid);
			this.eawsReport = report;
			this.eawsRiskLevel = crsId;
//			this.eawsRiskLevel = crsId + " " + eid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(this.getClass()).log(Level.ERROR, null, e);
		}
		
		this.classification=student.getEducationLevel().toFieldName();
		//the following information is filler and will be replaced when we have a means of retrieval
		this.readingSAT = 600;
		this.mathSAT = 700;
		this.writingSAT = 650;
		this.major = "Computer Science";
		
		DecimalFormat df = new DecimalFormat("#.000");
		this.GPA = Double.parseDouble(df.format(generator.nextDouble() * 2 + 2));
		this.photoLink = "http://delawarebpa.org/wp-content/uploads/2011/07/placeholder.png";
	}

	public BBUserHistory()
	{
		this.eid="";
	}

	public String getEid()
	{
		return this.eid;
	}


	public String getFullName()
	{
		return this.fullName;
	}

	public String getClassification()
	{
		return this.classification;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getReadingSAT() {
		return readingSAT;
	}

	public void setReadingSAT(int readingSAT) {
		this.readingSAT = readingSAT;
	}

	public int getMathSAT() {
		return mathSAT;
	}

	public void setMathSAT(int mathSAT) {
		this.mathSAT = mathSAT;
	}

	public int getWritingSAT() {
		return writingSAT;
	}

	public void setWritingSAT(int writingSAT) {
		this.writingSAT = writingSAT;
	}

	public int getTotalSAT() {
		return this.mathSAT+this.readingSAT+this.writingSAT;
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(double gPA) {
		GPA = gPA;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public StudentEAWSReport getEawsReport() {
		return eawsReport;
	}

	public void setEawsReport(StudentEAWSReport eawsReport) {
		this.eawsReport = eawsReport;
	}

	public String getEawsRiskLevel() {
		return eawsRiskLevel;
	}

	public void setEawsRiskLevel(String eawsRiskLevel) {
		this.eawsRiskLevel = eawsRiskLevel;
	}

}
