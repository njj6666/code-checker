package com.dxc.plm.codechecker.rule;

import com.dxc.plm.codechecker.utils.Constants;

public class RuleFactory {
	public RuleFactory() {
	}

	public Rule createRule(String fileType, String ruleName) {
		if (Constants.FILE_TYPE_SVB.equals(fileType)) {
			switch (ruleName) {
			case Constants.RULE_FILE_NAME:
				return new SVBFileNameRule();
			case Constants.RULE_VARIABLE_NAMING:
				return new SVBVariableNamingRule();
			default:
				break;
			}
		}
		return null;
	}

}
