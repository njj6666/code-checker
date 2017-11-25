package com.dxc.plm.codechecker.rule.vbs;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dxc.plm.codechecker.configuration.CodeCheckerConfiguration;
import com.dxc.plm.codechecker.model.CodeLine;
import com.dxc.plm.codechecker.model.Report;
import com.dxc.plm.codechecker.model.ReportItem;
import com.dxc.plm.codechecker.rule.Rule;
import com.dxc.plm.codechecker.rule.service.RuleService;
import com.dxc.plm.codechecker.rule.service.VBSRuleService;
import com.dxc.plm.codechecker.utils.Constants;
import com.dxc.plm.codechecker.utils.Validator;

public class VBSVariableNamingRule implements Rule {
	static CodeCheckerConfiguration config = CodeCheckerConfiguration.getInstance();
	static Properties messages = config.getMessages();
	static Logger log = Logger.getLogger(Validator.class.getName());
	private RuleService service = new VBSRuleService();

	@Override
	public void executeRule(String line) throws IOException {
		CodeLine codeLine = service.parseLine(line);
		String lineType = codeLine.getType();
		String trimLine = codeLine.getContent().trim();
		if (lineType.equals(Constants.LINE_TYPE_VAR)) {
			String var;
			if (trimLine.startsWith(Constants.LINE_DIM.toLowerCase())
					|| trimLine.startsWith(Constants.LINE_SET.toLowerCase())
					|| trimLine.startsWith(Constants.LINE_PUBLIC.toLowerCase())) {
				Report.addReportItem(new ReportItem(Report.getLineNumber(), messages.getProperty("rule.capModifier"),
						Report.getTargetFile(), trimLine));
			}
			if (trimLine.startsWith(Constants.LINE_SET)) {
				service.checkInitCap(trimLine);
				var = trimLine.substring(trimLine.indexOf(Constants.WHITESPACE), trimLine.indexOf(Constants.EQUAL_MARK))
						.trim();
				service.checkHungrian(var);
			} else if (trimLine.startsWith(Constants.LINE_DIM)) {
				if (!service.checkVarsCount(trimLine)) {
					var = trimLine.substring(trimLine.indexOf(Constants.WHITESPACE)).trim();
					service.checkHungrian(var);
				}
			} else if (trimLine.startsWith("Public g_")) {
				var = trimLine.substring(trimLine.indexOf(Constants.WHITESPACE)).trim();
				service.checkGlobalVar(var);
			} else if (trimLine.startsWith("g_") || trimLine.startsWith("m_")) {
				// var = trimLine.substring(0, trimLine.indexOf("=")).trim();
				// checkGlobalVar(var);
			}

		}
	}

}
