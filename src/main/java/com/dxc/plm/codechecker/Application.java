package com.dxc.plm.codechecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

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
	// static Logger log = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) throws Exception {
		// load configuration
		ApplicationConfiguration config = ApplicationConfiguration.getInstance();
		
		// the default workdir is to debug purpose
		// if user pass the workdir from command line, overwrite the default workdir path.
		String workDir = Constants.TEST_DATA;
		if (args.length > 0) {
			workDir = args[0];
		}
		
		// get a file list under workdir, which match the file type be configured.
		// TODO - Low - currently file types are configured in ApplicationConfiguration, should be configured in external config file.
		Utils utils = new Utils();
		List<File> fileList = utils.getFileList(workDir, config.getFileTypes());

		// if no files match the required file type, exit the application with code 0.
		if (fileList.size() == 0) {
			System.out.println(Constants.MESSAGE_NO_FILE_TO_CHECK);
			System.exit(0);
		}

		// analyze files one by one
		// Any issue will be stored in ApplicationContext.results
		CheckerFactory factory = new CheckerFactory();
		String targetFileName = null;
		String targetFileType = null;
		for (File file : fileList) {
			ApplicationContext.targetFile = file;
			ApplicationContext.lineNumber = Constants.FIRST_LINE;
			// parse fileName, get file extension as file type.
			targetFileName = file.getName();
			targetFileType = targetFileName.substring(targetFileName.indexOf(Constants.DOT)+1);
			Checker checker = factory.createChecker(targetFileType, config.getRules());
			checker.analyze(targetFileType, config.getRules());
		}

		// Report the results
		// If no issue, exit with code 0.
		if (ApplicationContext.results.size() == 0) {
			System.out.println(Constants.MESSAGE_NO_ISSUE);
			System.exit(0);
		}
		
		File report = null;
		// if user passes "jenkins" instead of workdir path, generate report at same directory of application.
		if (Constants.JENKINS.equals(workDir)) {
			report = new File(Constants.REPORT);
		} else {
			report = new File(System.getProperty("user.home") + "/" + Constants.REPORT);
		}
		System.out.println("Report location:");
		System.out.println(report);
		if (report.exists())
			report.delete();
		report.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(report);
		PrintStream printStream = new PrintStream(fileOutputStream);
		System.setOut(printStream);
		for (Result result : ApplicationContext.results) {
			System.out.println(result.toString());
		}
	}
}
