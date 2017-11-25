package com.dxc.plm.codechecker;

import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.utils.Constants;

@Component
public class CheckerFactory {

	public Checker createChecker(String fileType) {
		Checker checker;
		if (Constants.SVB.equalsIgnoreCase(fileType)) {
			checker = new VBSChecker();
		} else {
			checker = null;
		}
		return checker;
	}

}
