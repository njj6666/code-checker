package com.dxc.plm.codechecker;

import java.util.List;

import com.dxc.plm.codechecker.rule.Rule;
import com.dxc.plm.codechecker.rule.RuleFactory;

public class Checker {
	public void analyze(String fileType, List<String> ruleNames) {
		RuleFactory factory = new RuleFactory();
		for (String ruleName : ruleNames) {
			Rule rule = factory.createRule(fileType, ruleName);
			rule.execute();
		}
	}
}
