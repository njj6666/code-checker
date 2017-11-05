package com.dxc.plm.codechecker;

import java.util.List;

import com.dxc.plm.codechecker.utils.Constants;

public class CheckerFactory {

	public Checker createChecker(String fileType, List<String> rules) {
		Checker checker;
		if (Constants.FILE_TYPE_SVB.equalsIgnoreCase(fileType)) {
			checker = new SVBChecker(fileType, rules);
		} else {
			checker = null;
		}
		return checker;
	}

}
