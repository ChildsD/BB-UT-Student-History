/*
 * Created on Jan 25, 2006
 */
package edu.utexas.ctl.bb.histool.services;

import blackboard.data.gradebook.Lineitem;

public class ListItemComparator implements java.util.Comparator {
    public int compare(Object o1, Object o2) {
        String s1 = ((Lineitem)o1).getName();
        String s2 = ((Lineitem)o2).getName();
        return s1.compareTo(s2);
    }

}
