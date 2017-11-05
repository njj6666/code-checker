package com.dxc.plm.codechecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import com.dxc.plm.codechecker.model.Result;
import com.dxc.plm.codechecker.utils.ApplicationConfiguration;
import com.dxc.plm.codechecker.utils.ApplicationContext;
import com.dxc.plm.codechecker.utils.Constants;
import com.dxc.plm.codechecker.utils.Utils;

/**
 * 
 * @author Robin Niu
 * @Company DXC
 * @date 01/10/2017
 */
public class Application {
	static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws Exception {
		// load configuration
		ApplicationConfiguration config = ApplicationConfiguration.getInstance();
		Properties messages = config.getMessages();

		// the default workdir is to debug purpose
		// if user pass the workdir from command line, overwrite the default workdir
		// path.
		String workDir = Constants.TEST_DATA_DIR;
		if (args.length > 0) {
			if(args[0].equalsIgnoreCase(Constants.OPTION_V)) {
				System.out.println(Constants.APP_VERSION);
				System.exit(0);
			}
			workDir = args[0];
		}

		// get a file list under workdir, which match the file type be configured.
		// TODO - Low - currently file types are configured in ApplicationConfiguration,
		// should be configured in external config file.
		Utils utils = new Utils();
		List<File> fileList = utils.getFileList(workDir, config.getFileTypes());

		// if no files match the required file type, exit the application with code 0.
		if (fileList.isEmpty()) {
			log.info(messages.getProperty("message.noFileToCheck"));
			System.exit(0);
		}

		// analyze files one by one
		// Any issue will be stored in ApplicationContext.results
		CheckerFactory factory = new CheckerFactory();
		String targetFileName = null;
		String targetFileType = null;
		for (File file : fileList) {
			ApplicationContext.setTargetFile(file);
			ApplicationContext.setLineNumber(Constants.FIRST_LINE);
			// parse fileName, get file extension as file type.
			targetFileName = file.getName();
			targetFileType = targetFileName.substring(targetFileName.indexOf(Constants.DOT) + 1);
			Checker checker = factory.createChecker(targetFileType, config.getRules());
			if (checker != null) {
				checker.analyze();
			} else {
				log.error(messages.getProperty("error.noCheck"));
				System.exit(1);
			}
		}

		// Report the results
		// If no issue, exit with code 0.
		if (ApplicationContext.getResults().isEmpty()) {
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
			for (Result result : ApplicationContext.getResults()) {
				bw.write(result.toString());
				bw.newLine();
			}
		}
		System.out.println("Report location:");
		System.out.println(report);
		System.out.println("More information see application.log please.");
	}
}
