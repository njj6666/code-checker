package com.dxc.plm.codechecker;

import java.util.List;

public class SVBChecker extends Checker {
	public List<String> ruleNames;
	public String fileType;

	public SVBChecker(String fileType, List<String> ruleNames) {
		this.ruleNames = ruleNames;
		this.fileType = fileType;
	}

}
