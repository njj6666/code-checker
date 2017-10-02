package com.dxc.plm.codechecker;

import java.util.List;

import com.dxc.plm.codechecker.utils.Constants;

public class CheckerFactory {
	public CheckerFactory() {
	}

	public Checker createChecker(String fileType, List<String> rules) {
		Checker checker;
		switch (fileType) {
		case Constants.FILE_TYPE_SVB:
			checker = new SVBChecker(fileType,rules);
			break;
		default:
			checker = new SVBChecker(fileType,rules);
			break;
		}
		return checker;
	}

}
