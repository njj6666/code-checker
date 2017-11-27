package com.dxc.plm.codechecker.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dxc.plm.codechecker.utils.Constants;

public class CodeCheckerConfiguration extends Properties {
	static Logger log = Logger.getLogger(CodeCheckerConfiguration.class.getName());

	private static final long serialVersionUID = 1L;

	private Properties messages;

	private static CodeCheckerConfiguration config;

	private CodeCheckerConfiguration() {
	}

	public static CodeCheckerConfiguration getInstance() {
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
		config = new CodeCheckerConfiguration();
		InputStream in = new FileInputStream(path);
		config.load(in);
	}

	public List<String> getFileTypes() {
		return Arrays.asList(config.getProperty("file.type.to.check").split(","));
	}

	public List<String> getRules() {
		return Arrays.asList(config.getProperty("rules.to.validate").split(","));
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

	public Properties getMessages() {
		// load message from messages.properties
		if (messages == null) {
			messages = new Properties();
			ClassLoader classLoader = getClass().getClassLoader();
			try (InputStream messageFile = classLoader.getResourceAsStream("messages.properties")) {
				messages.load(messageFile);
			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}
		return messages;
	}

}
