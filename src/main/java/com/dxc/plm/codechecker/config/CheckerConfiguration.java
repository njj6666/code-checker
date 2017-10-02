package com.dxc.plm.codechecker.config;

import java.util.ArrayList;
import java.util.List;

import com.dxc.plm.codechecker.utils.Constants;

public class CheckerConfiguration {
	public List<String> fileTypes;
	
	public List<String> rules;

	
	public CheckerConfiguration() {
		this.fileTypes = new ArrayList<String>();
		this.fileTypes.add(Constants.FILE_TYPE_SVB);
		
		this.rules = new ArrayList<String>();
	//	this.rules.add(Constants.RULE_FILE_NAME);
		this.rules.add(Constants.RULE_VARIABLE_NAMING);
	}
	public List<String> getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(List<String> fileTypes) {
		this.fileTypes = fileTypes;
	}

	public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	}
}
