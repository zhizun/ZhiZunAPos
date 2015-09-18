package com.ch.epw.phone.view;

import java.util.Comparator;

import com.zhizun.pos.bean.PhoneContactBean;


public class PinyinComparator implements Comparator<PhoneContactBean> {

	public int compare(PhoneContactBean o1, PhoneContactBean o2) {
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
