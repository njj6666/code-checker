package com.dxc.plm.codechecker.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.Configuration;

public class ApplicationConfiguration extends Properties{
	public String applications[];
	public String projects[];
	public String utilities[];
	
	public List<String> fileTypes;
	
	public List<String> rules;
	
	public String jenkins_workspace;
	
	static private ApplicationConfiguration config;
	
	private ApplicationConfiguration(){
		this.fileTypes = new ArrayList<String>();
		this.fileTypes.add(Constants.FILE_TYPE_SVB);
		
		this.rules = new ArrayList<String>();
	//	this.rules.add(Constants.RULE_FILE_NAME);
		this.rules.add(Constants.RULE_VARIABLE_NAMING);

	}
	
	public static ApplicationConfiguration getInstance(){
		if(config == null){
			try {
				loadConf(Constants.CONFIG_FILE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return config;
	}
	
	public static void loadConf(String path) throws Exception {
		config = new ApplicationConfiguration();
		InputStream in = new FileInputStream(path);
		config.load(in);
	}

	
//	public CheckerConfiguration() {
//		this.fileTypes = new ArrayList<String>();
//		this.fileTypes.add(Constants.FILE_TYPE_SVB);
//		
//		this.rules = new ArrayList<String>();
//	//	this.rules.add(Constants.RULE_FILE_NAME);
//		this.rules.add(Constants.RULE_VARIABLE_NAMING);
//	}
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

	public String[] getApplications() {
		return config.getProperty("function.application.prefix").split(",");
	}

	public void setApplications(String[] applications) {
		this.applications = applications;
	}

	public String[] getProjects() {
		return config.getProperty("function.project.prefix").split(",");
	}

	public void setProjects(String[] projects) {
		this.projects = projects;
	}

	public String[] getUtilities() {
		return config.getProperty("function.utility.prefix").split(",");
	}

	public void setUtilities(String[] utilities) {
		this.utilities = utilities;
	}

	public String getJenkins_workspace() {
		return config.getProperty("jenkins.workspace.dir");
	}

	public void setJenkins_workspace(String jenkins_workspace) {
		this.jenkins_workspace = jenkins_workspace;
	}
}
