package com.dxc.plm.codechecker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dxc.plm.codechecker.configuration.CodeCheckerConfiguration;
import com.dxc.plm.codechecker.model.Result;

public class Validator {
	static CodeCheckerConfiguration config = CodeCheckerConfiguration.getInstance();
	static Properties messages = config.getMessages();
	static Logger log = Logger.getLogger(Validator.class.getName());

	public static void validateNaming(String line) {
		String lineType = checkLineType(line);
		String trimLine = line.trim();
		if (lineType.equals(Constants.LINE_TYPE_VAR)) {
			String var;
			if (trimLine.startsWith(Constants.LINE_DIM.toLowerCase()) || trimLine.startsWith(Constants.LINE_SET.toLowerCase()) || trimLine.startsWith(Constants.LINE_PUBLIC.toLowerCase())) {
				GlobalVar.getResults().add(
						new Result(GlobalVar.getLineNumber(), messages.getProperty("rule.capModifier"), GlobalVar.getTargetFile(), line));
			}
			if (trimLine.startsWith(Constants.LINE_SET)) {
				checkInitial(trimLine);
				var = trimLine.substring(trimLine.indexOf(Constants.WHITESPACE), trimLine.indexOf(Constants.EQUAL_MARK)).trim();
				checkHungrian(var);
			} else if (trimLine.startsWith(Constants.LINE_DIM)) {
				if (!checkVarsCount(trimLine)) {
					var = trimLine.substring(trimLine.indexOf(Constants.WHITESPACE)).trim();
					checkHungrian(var);
				}
			} else if (trimLine.startsWith("Public g_")) {
				var = trimLine.substring(trimLine.indexOf(Constants.WHITESPACE)).trim();
				checkGlobalVar(var);
			} else if (trimLine.startsWith("g_") || trimLine.startsWith("m_")) {
				//var = trimLine.substring(0, trimLine.indexOf("=")).trim();
				//checkGlobalVar(var);
			} 

		} else if(lineType.equals(Constants.LINE_TYPE_FUNCTION)) {
			String functionName = getFunctionName(trimLine);
			checkFunctionNaming(functionName);
			
		}
		GlobalVar.setLineNumber(GlobalVar.getLineNumber()+1);
	}

	public static void checkOverviewComment(String line) {
		if (!line.startsWith("'#")) {
			GlobalVar.getResults().add(new Result(GlobalVar.getLineNumber(), "No overview comments", GlobalVar.getTargetFile(), line));
		}
	}

	public static void checkGlobalVar(String var) {
		if (!var.startsWith("g_")) {
			GlobalVar.getResults().add(new Result(GlobalVar.getLineNumber(), messages.getProperty("rule.invalidGlobalVarName"), GlobalVar.getTargetFile(), var));
		} else {
			if (!var.endsWith("Page_URL")) {
				checkHungrian(var.substring(2));
			}
		}
	}

	public static boolean checkVarsCount(String line) {
		if (line.contains(Constants.COMMA)) {
			GlobalVar.getResults().add(new Result(GlobalVar.getLineNumber(), messages.getProperty("rule.tooManyVars"), GlobalVar.getTargetFile(), line));
			return true;
		}
		return false;
	}

	public static void checkInitial(String line) {
		if (!line.contains(Constants.EQUAL_MARK)) {
			GlobalVar.getResults().add(new Result(GlobalVar.getLineNumber(), messages.getProperty("rule.varIsNotInitial"), GlobalVar.getTargetFile(), line));
		}
	}

	public static void checkHungrian(String var) {
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
			GlobalVar.getResults().add(new Result(GlobalVar.getLineNumber(), messages.getProperty("rule.varNaming"), GlobalVar.getTargetFile(), var));
		}

	}

	public static String checkLineType(String line) {
		String trimLine = line.trim().toLowerCase();
		String type = "";
		if (trimLine.startsWith(Constants.LINE_DIM.toLowerCase())
				|| trimLine.startsWith(Constants.LINE_SET.toLowerCase())
				|| trimLine.startsWith(Constants.LINE_PUBLIC.toLowerCase()) 
				|| trimLine.startsWith("g_")
				|| trimLine.startsWith("m_")) {
			type = Constants.LINE_TYPE_VAR;
		} else if (trimLine.startsWith(Constants.LINE_COMMENT.toLowerCase())) {
			type = Constants.LINE_TYPE_COMMENT;
		} else if (trimLine.startsWith(Constants.LINE_FUNCTION.toLowerCase())) {
			type = Constants.LINE_TYPE_FUNCTION;
		}
		return type;

	}
	
	public static void checkFunctionNaming(String functionName) {
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
			GlobalVar.getResults().add(new Result(GlobalVar.getLineNumber(), messages.getProperty("rule.functionNaming"), GlobalVar.getTargetFile(), functionName));
		}
	}
	
	public static String getFunctionName(String line) {
		if(line.contains("(")) {
			return line.substring(line.indexOf(Constants.WHITESPACE), line.indexOf("(")).trim();
		}else {
			return line.substring(line.indexOf(Constants.WHITESPACE)).trim();
		}
		
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = originalFormat.parse("1497435840730");
		System.out.println(date);
	}
	

}
