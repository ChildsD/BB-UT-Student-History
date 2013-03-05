package edu.utexas.ctl.bb.histool.services;

import java.util.Comparator;

import edu.utexas.ctl.bb.histool.entities.BBUserHistory;

public class SATComparator implements Comparator<BBUserHistory>{

	public int compare(BBUserHistory his1, BBUserHistory his2) {
		int SAT1 = his1.getTotalSAT();
		int SAT2 = his2.getTotalSAT();
		if(SAT1 < SAT2)
			return -1;
		if(SAT1 > SAT2)
			return 1;
		return 0;
	}

}
