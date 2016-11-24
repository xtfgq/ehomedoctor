package com.zzu.ehome.ehomefordoctor.utils;


import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<UsersBySignDoctor> {

	public int compare(UsersBySignDoctor o1, UsersBySignDoctor o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
