package com.dxc.plm.codechecker.rule.vbs;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.configuration.CodeCheckerConfiguration;
import com.dxc.plm.codechecker.model.CodeLine;
import com.dxc.plm.codechecker.model.Report;
import com.dxc.plm.codechecker.model.ReportItem;
import com.dxc.plm.codechecker.rule.Rule;
import com.dxc.plm.codechecker.rule.service.RuleService;
import com.dxc.plm.codechecker.utils.Constants;

@Component("vbsFunctionNamingRule")
public class VBSFunctionNamingRule implements Rule {
	static CodeCheckerConfiguration config = CodeCheckerConfiguration.getInstance();
	static Properties messages = config.getMessages();
	static Logger log = Logger.getLogger(VBSFunctionNamingRule.class.getName());
	
	@Autowired
	@Qualifier("vbsRuleService")
	private RuleService service;

	@Override
	public void executeRule(String line) throws IOException {
		CodeLine codeLine = service.parseLine(line);
		String lineType = codeLine.getType();
		String trimLine = codeLine.getContent().trim();
		if (lineType.equals(Constants.LINE_TYPE_FUNCTION)) {
			String functionName = service.getFunctionName(trimLine);
			checkFunctionNaming(functionName);
		}
	}
	
	private void checkFunctionNaming(String functionName) {
		boolean flag = true;
		if(!Character.isUpperCase(functionName.charAt(0))) {
			flag = false;
		}else {
			flag = false;
			for(String util :config.getUtilities()) {
				if (functionName.startsWith(util+"_")) {
					flag = true;
					break;
				}
			}
		}
		
		if(flag)
			return;	
		
		String app_prefix="";
		for(String app :config.getApplications()) {
			if (functionName.startsWith(app)) {
				app_prefix = app;
				flag = true;
				break;
			}else {
				flag = false;
			}
		}
		
		String singleAppPrefix = app_prefix+Constants.UNDERSCORE;
		if(flag && functionName.startsWith(singleAppPrefix)) {
			String functionNameWithoutSingleAppPrefix = functionName.substring(singleAppPrefix.length());
			if(!Character.isUpperCase(functionNameWithoutSingleAppPrefix.charAt(0))) {
				flag = false;
			}
			if(flag) {
				return;
			}
		}
		
		String prj_prefix="";
		if(flag) {
			for(String project :config.getProjects()) {
				String funcNameWithoutApp = functionName.substring(app_prefix.length());
				if (funcNameWithoutApp.startsWith(project+"_")) {
					prj_prefix = project;
					flag = true;
					break;
				}else {
					flag = false;
				}
			}	
		}

		if(flag) {
			String prefix = app_prefix + prj_prefix + Constants.UNDERSCORE;
			String functionNameWithoutPrefix = functionName.substring(prefix.length());
			if(!Character.isUpperCase(functionNameWithoutPrefix.charAt(0))) {
				flag = false;
			}
		}
		
		if(!flag) {
			Report.getReportItems().add(new ReportItem(Report.getLineNumber(), messages.getProperty("rule.functionNaming"), Report.getTargetFile(), functionName));
		}
	}

}
