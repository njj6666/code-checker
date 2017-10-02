package com.dxc.plm.codechecker.utils;

import com.dxc.plm.codechecker.Main;
import com.dxc.plm.codechecker.model.Result;

public class Validator {

	public static void validateNaming(String line) {
		String lineType = checkLineType(line);
		String trimLine = line.trim();
		if (lineType.equals(Constants.LINE_TYPE_VAR)) {
			String var;
			if (trimLine.startsWith("dim") || trimLine.startsWith("set") || trimLine.startsWith("public")) {
				Main.results.add(
						new Result(Main.lineNumber, "Using Dim/Set/Public, Not dim/set/public", Main.targetFile, line));
			}
			if (trimLine.startsWith(Constants.LINE_SET)) {
				checkInitial(trimLine);
				var = trimLine.substring(trimLine.indexOf(" "), trimLine.indexOf("=")).trim();
				checkHungrian(var);
			} else if (trimLine.startsWith(Constants.LINE_DIM)) {
				if (!checkVarsCount(trimLine)) {
					var = trimLine.substring(trimLine.indexOf(" ")).trim();
					checkHungrian(var);
				}
			} else if (trimLine.startsWith("Public g_")) {
				var = trimLine.substring(trimLine.indexOf(" ")).trim();
				checkGlobalVar(var);
			} else if (trimLine.startsWith("g_") || trimLine.startsWith("m_")) {
				//var = trimLine.substring(0, trimLine.indexOf("=")).trim();
				//checkGlobalVar(var);
			}
		}
		Main.lineNumber++;
	}

	public static void main(String[] args) throws Exception {
		String var = "arrGetObj";
		System.out.println(Character.isUpperCase(var.charAt(3)));
	}

	public static void checkOverviewComment(String line) {
		if (!line.startsWith("'#")) {
			Main.results.add(new Result(Main.lineNumber, "No overview comments", Main.targetFile, line));
		}
	}

	public static void checkGlobalVar(String var) {
		if (!var.startsWith("g_")) {
			Main.results.add(new Result(Main.lineNumber, "Invalid global var name", Main.targetFile, var));
		} else {
			if (!var.endsWith("Page_URL")) {
				checkHungrian(var.substring(2));
			}
		}
	}

	public static boolean checkVarsCount(String line) {
		if (line.contains(",")) {
			Main.results.add(new Result(Main.lineNumber, "Too many vars in a line", Main.targetFile, line));
			return true;
		}
		return false;
	}

	public static void checkInitial(String line) {
		if (!line.contains("=")) {
			Main.results.add(new Result(Main.lineNumber, "variable is not initial", Main.targetFile, line));
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
			Main.results.add(new Result(Main.lineNumber, "variable naming", Main.targetFile, var));
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
		} else if (trimLine.toLowerCase().startsWith(Constants.LINE_COMMENT.toLowerCase())) {
			type = Constants.LINE_TYPE_COMMENT;
		} else if (trimLine.toLowerCase().startsWith(Constants.LINE_FUNCTION.toLowerCase())) {
			type = Constants.LINE_TYPE_FUNCTION;
		}
		return type;

	}

}
