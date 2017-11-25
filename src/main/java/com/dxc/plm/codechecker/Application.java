package com.dxc.plm.codechecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.configuration.AppConfig;
import com.dxc.plm.codechecker.configuration.CodeCheckerConfiguration;
import com.dxc.plm.codechecker.model.Report;
import com.dxc.plm.codechecker.model.ReportItem;
import com.dxc.plm.codechecker.utils.Constants;
import com.dxc.plm.codechecker.utils.Utils;

/**
 * 
 * @author Robin Niu
 * @Company DXC
 * @date 01/10/2017
 */
@Component
public class Application {
	static Logger log = Logger.getLogger(Application.class.getName());
	@Autowired
	private Utils utils;
	
	@Autowired
	private CheckerFactory checkerFactory;
	
	public static void main(String[] args) throws Exception {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Application app = (Application)context.getBean("application");
		List<String> arguments = Arrays.asList(args);
		app.run(arguments);
		
		context.close();
	}

	public void run(List<String> arguments) throws Exception {
		// load configuration
		CodeCheckerConfiguration config = CodeCheckerConfiguration.getInstance();
		Properties messages = config.getMessages();

		// the default workdir is to debug purpose
		// if user pass the workdir from command line, overwrite the default workdir path.
		String workDir = Constants.TEST_DATA_DIR;
		if (arguments != null && arguments.size() > 0) {
			if(arguments.get(0).equalsIgnoreCase(Constants.OPTION_V)) {
				System.out.println(Constants.APP_VERSION);
				System.exit(0);
			}
			workDir = arguments.get(0);
		}

		// get a file list under workdir, which match the file type be configured.
		List<File> fileList = utils.getFileList(workDir, config.getFileTypes());

		// if no files match the required file type, exit the application with code 0.
		if (fileList.isEmpty()) {
			log.info(messages.getProperty("message.noFileToCheck"));
			System.exit(0);
		}

		// analyze files one by one
		// Any issue will be stored in Report.results
		String targetFileName = null;
		String targetFileType = null;
		for (File file : fileList) {
			Report.setTargetFile(file);
			Report.setLineNumber(Constants.FIRST_LINE);
			// parse fileName, get file extension as file type.
			targetFileName = file.getName();
			targetFileType = targetFileName.substring(targetFileName.indexOf(Constants.DOT) + 1);
			Checker checker = checkerFactory.createChecker(targetFileType);
			if (checker != null) {
				checker.registerRules(config.getRules());
				checker.process();
			} else {
				log.error(messages.getProperty("error.noCheck"));
				System.exit(1);
			}
		}

		// Report the results
		// If no issue, exit with code 0.
		if (Report.getReportItems().isEmpty()) {
			log.info(messages.getProperty("message.noIssue"));
			System.exit(0);
		}

		File report = null;
		// if user passes "jenkins" instead of workdir path, generate report at same
		// directory of application.
		if (Constants.JENKINS.equals(workDir)) {
			report = new File(Constants.REPORT);
		} else {
			report = new File(System.getProperty("user.home") + Constants.DIRECTORY_DELIMER + Constants.REPORT);
		}

		if (report.exists() && !report.delete()) {
			log.error(messages.getProperty("error.reportDelete"));
			System.exit(1);
		}

		if (!report.createNewFile()) {
			log.error(messages.getProperty("error.reportCreate"));
			System.exit(1);
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(report);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));) {
			for (ReportItem result : Report.getReportItems()) {
				bw.write(result.toString());
				bw.newLine();
			}
		}
		System.out.println("Report location:");
		System.out.println(report);
		System.out.println("More information see application.log please.");
	}
}
