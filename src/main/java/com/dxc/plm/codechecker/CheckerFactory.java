package com.dxc.plm.codechecker;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.utils.Constants;

@Component
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
