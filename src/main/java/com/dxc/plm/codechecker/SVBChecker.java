package com.dxc.plm.codechecker;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.rule.Rule;
import com.dxc.plm.codechecker.rule.RuleFactory;
@Component
public class SVBChecker implements Checker {
	private List<String> ruleNames;
	private String fileType;
	@Autowired(required=true)
	private RuleFactory factory;
	
	public RuleFactory getFactory() {
		return this.factory;
	}

	public SVBChecker() {}

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
