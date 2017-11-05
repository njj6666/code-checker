package com.dxc.plm.codechecker;

import java.io.IOException;
import java.util.List;

import com.dxc.plm.codechecker.rule.Rule;
import com.dxc.plm.codechecker.rule.RuleFactory;

public class SVBChecker implements Checker {
	private List<String> ruleNames;
	private String fileType;

	public SVBChecker(String fileType, List<String> ruleNames) {
		this.ruleNames = ruleNames;
		this.fileType = fileType;
	}
	
	public void analyze() throws IOException {
		RuleFactory factory = new RuleFactory();
		for (String ruleName : this.ruleNames) {
			Rule rule = factory.createRule(this.fileType, ruleName);
			rule.execute();
		}
	}

}
