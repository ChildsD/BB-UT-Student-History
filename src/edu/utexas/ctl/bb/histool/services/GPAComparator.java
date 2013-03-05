package edu.utexas.ctl.bb.histool.services;

import java.util.Comparator;

import edu.utexas.ctl.bb.histool.entities.BBUserHistory;

public class GPAComparator implements Comparator<BBUserHistory>{

	public int compare(BBUserHistory his1, BBUserHistory his2) {
		double GPA1 = his1.getGPA();
		double GPA2 = his2.getGPA();
		if(GPA1 < GPA2)
			return -1;
		if(GPA1 > GPA2)
			return 1;
		return 0;
	}

}
