package com.dxc.plm.codechecker.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationConfiguration extends Properties {
	static Logger log = Logger.getLogger(ApplicationConfiguration.class.getName());

	private static final long serialVersionUID = 1L;

	private Properties messages;

	private List<String> fileTypes;

	private List<String> rules;

	private static ApplicationConfiguration config;

	private ApplicationConfiguration() throws IOException {
		this.fileTypes = new ArrayList<>();
		this.fileTypes.add(Constants.FILE_TYPE_SVB);

		this.rules = new ArrayList<>();
		this.rules.add(Constants.RULE_VARIABLE_NAMING);
		
		// load message from messages.properties 
		this.messages = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		File messageFile = new File(classLoader.getResource("messages.properties").getFile());
		InputStream in = new FileInputStream(messageFile);
		this.messages.load(in);
		in.close();
	}

	public Properties getMessages() {
		return messages;
	}

	public void setMessages(Properties messages) {
		this.messages = messages;
	}

	public static ApplicationConfiguration getInstance() {
		if (config == null) {
			try {
				loadConf(Constants.CONFIG_FILE);
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
		}
		return config;
	}

	public static void loadConf(String path) throws IOException {
		config = new ApplicationConfiguration();
		InputStream in = new FileInputStream(path);
		config.load(in);
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

	public String[] getApplications() {
		return config.getProperty("function.application.prefix").split(",");
	}

	public String[] getProjects() {
		return config.getProperty("function.project.prefix").split(",");
	}

	public String[] getUtilities() {
		return config.getProperty("function.utility.prefix").split(",");
	}

	public String getJenkinsWorkspace() {
		return config.getProperty("jenkins.workspace.dir");
	}

}
