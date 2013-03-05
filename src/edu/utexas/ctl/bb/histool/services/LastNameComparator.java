package edu.utexas.ctl.bb.histool.services;

import java.util.Comparator;

import edu.utexas.ctl.bb.histool.entities.BBUserHistory;

public class LastNameComparator implements Comparator<BBUserHistory>{

	public int compare(BBUserHistory his1, BBUserHistory his2) {
		return his1.getLastName().compareToIgnoreCase(his2.getLastName());
	}

}
