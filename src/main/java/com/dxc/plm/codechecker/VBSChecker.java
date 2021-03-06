package com.dxc.plm.codechecker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.rule.Rule;
import com.dxc.plm.codechecker.utils.Constants;

@Component
public class VBSChecker extends Checker {
	public VBSChecker() {
	}
	
	public void registerRules(List<String> ruleNames) {
		if (this.rules == null) {
			this.rules = new ArrayList<Rule>();
		}
		for (String ruleName : ruleNames) {
			switch (ruleName) {
			case Constants.RULE_FILE_NAMING:
				this.rules.add((Rule)Application.context.getBean("vbsFileNamingRule"));
				break;
			case Constants.RULE_VARIABLE_NAMING:
				this.rules.add((Rule)Application.context.getBean("vbsVariableNamingRule"));
				break;
			case Constants.RULE_Function_NAMING:
				this.rules.add((Rule)Application.context.getBean("vbsFunctionNamingRule"));
				break;
			default:
				break;
			}
		}
	}

}
