package com.dxc.plm.codechecker.rule.service;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dxc.plm.codechecker.configuration.CodeCheckerConfiguration;
import com.dxc.plm.codechecker.model.Report;
import com.dxc.plm.codechecker.model.ReportItem;
import com.dxc.plm.codechecker.utils.Constants;

@Service
public abstract class GeneralRuleService implements RuleService {
	static Logger log = Logger.getLogger(GeneralRuleService.class.getName());
	CodeCheckerConfiguration config = CodeCheckerConfiguration.getInstance();
	Properties messages = config.getMessages();

	// 变量是否初始化
	@Override
	public void isInitilize(String line) {
		if (!line.contains(Constants.EQUAL_MARK)) {
			Report.addReportItem(new ReportItem(Report.getLineNumber(), messages.getProperty("rule.varIsNotInitial"),
					Report.getTargetFile(), line));
		}
	}

	// 是否匈牙利命名法
	@Override
	public void checkHungrian(String var) {
		boolean flag = true;
		if (var.startsWith("obj") || var.startsWith("dtm") || var.startsWith("arr")) {
			if (var.length() > 3) {
				flag = Character.isUpperCase(var.charAt(3));
			} else {
				flag = false;
			}
		} else if (var.startsWith("b") || var.startsWith("s") || var.startsWith("n")) {
			if (var.length() > 1) {
				flag = Character.isUpperCase(var.charAt(1));
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		if (!flag) {
			Report.addReportItem(new ReportItem(Report.getLineNumber(), messages.getProperty("rule.varNaming"),
					Report.getTargetFile(), var));
		}
	}

	// 是否一行定义多个变量
	@Override
	public boolean checkVarsCount(String line) {
		if (line.contains(Constants.COMMA)) {
			Report.getReportItems().add(new ReportItem(Report.getLineNumber(), messages.getProperty("rule.tooManyVars"),
					Report.getTargetFile(), line));
			return true;
		}
		return false;
	}

}
