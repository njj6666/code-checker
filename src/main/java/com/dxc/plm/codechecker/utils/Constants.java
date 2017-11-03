package com.dxc.plm.codechecker.utils;

public class Constants {
	public final static String FILE_TYPE_SVB = "svb";
	public final static int FIRST_LINE = 1;
	public final static String DOT = ".";
	public final static String REPORT = "check_output.csv";


	public final static String RULE_FILE_NAME = "FileNameRule";
	public final static String RULE_VARIABLE_NAMING = "VariableNaming";
	
	public final static String LINE_DIM = "Dim";
	public final static String LINE_SET = "Set";
	public final static String LINE_PUBLIC = "Public";
	public final static String LINE_COMMENT = "'";
	public final static String LINE_FUNCTION = "Function";
	
	public final static String LINE_TYPE_VAR = "variable";
	public final static String LINE_TYPE_COMMENT = "comment";
	public final static String LINE_TYPE_FUNCTION = "function";
	
	public final static String APPLICATIONS[] = {"Enovia"};
	public final static String PROJECTS[] = {"PDT","DSM"};
	public final static String UTILITY[] = {"Excel","DB"};
	
	public final static String JENKINS = "jenkins";
	
	public final static String CONFIG_FILE = "./config";
	public final static String TEST_DATA = "testdata/Script";
	
	public final static String MESSAGE_NO_FILE_TO_CHECK = "No applicatable files to check.";
	public final static String MESSAGE_NO_ISSUE = "Congrats! Your code style is cool!";
	
}
