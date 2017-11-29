package com.dxc.plm.codechecker.rule.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.model.CodeLine;
import com.dxc.plm.codechecker.model.Report;
import com.dxc.plm.codechecker.model.ReportItem;
import com.dxc.plm.codechecker.utils.Constants;

@Component("vbsRuleService")
@Qualifier("vbsRuleService")
public class VBSRuleService extends GeneralRuleService {
	static Logger log = Logger.getLogger(VBSRuleService.class.getName());
	
	@Override
	public CodeLine parseLine(String line) {
		// lineType indicate current line is a variable/function/comment etc.
		String lineType = checkLineType(line.trim());
		CodeLine codeLine = new CodeLine(line);
		codeLine.setType(lineType);
		return codeLine;
	}
	
	@Override
	public String checkLineType(String line) {
		String trimLine = line.trim().toLowerCase();
		String type = "";
		if (trimLine.startsWith(Constants.LINE_DIM.toLowerCase())
				|| trimLine.startsWith(Constants.LINE_SET.toLowerCase())
				|| trimLine.startsWith(Constants.LINE_PUBLIC.toLowerCase()) || trimLine.startsWith("g_")
				|| trimLine.startsWith("m_")) {
			type = Constants.LINE_TYPE_VAR;
		} else if (trimLine.startsWith(Constants.LINE_COMMENT.toLowerCase())) {
			type = Constants.LINE_TYPE_COMMENT;
		} else if (trimLine.startsWith(Constants.LINE_FUNCTION.toLowerCase())) {
			type = Constants.LINE_TYPE_FUNCTION;
		}
		return type;
	}
	
	public String getFunctionName(String line) {
		if(line.contains("(")) {
			return line.substring(line.indexOf(Constants.WHITESPACE), line.indexOf("(")).trim();
		}else {
			return line.substring(line.indexOf(Constants.WHITESPACE)).trim();
		}
	}
	
	// check if global variable or not
		@Override
		public void checkGlobalVar(String var) {
			if (!var.startsWith("g_")) {
				Report.getReportItems().add(new ReportItem(Report.getLineNumber(), messages.getProperty("rule.invalidGlobalVarName"), Report.getTargetFile(), var));
			} else {
				if (!var.endsWith("Page_URL")) {
					checkHungrian(var.substring(2));
				}
			}
		}
}
