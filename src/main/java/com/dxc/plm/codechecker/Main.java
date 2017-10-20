package com.dxc.plm.codechecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.dxc.plm.codechecker.config.CheckerConfiguration;
import com.dxc.plm.codechecker.model.Result;
import com.dxc.plm.codechecker.utils.Constants;
import com.dxc.plm.codechecker.utils.Utils;

/**
 * 
 * @author Robin
 * @date 01/10/2017
 * @version 1.0
 */
public class Main {

	//static Logger log = Logger.getLogger(Main.class.getName());
	public static File targetFile;
	public static int lineNumber;
	public static List<Result> results = new ArrayList<Result>();

	public static void main(String[] args) throws Exception {

		// load configuration
		CheckerConfiguration config = CheckerConfiguration.getInstance("./config");
		Utils utils = new Utils();
		//the initial workdir is for debug
		String workDir = "C:\\Users\\niujij\\workspace\\plm-code-checker\\data\\AutomatedTesting";
		if(args.length>0) {
			workDir = args[0];
		}
		
		List<File> fileList = utils.getFileList(workDir, config.getFileTypes());
		
		if(fileList.size()==0) {
			System.exit(0);
		}
		
			// List<File> fileList =
			// utils.getFileList("C:\\Users\\niujij\\workspace\\PG_Project\\trunk\\src",
			// config.getFileTypes());
	

		CheckerFactory factory = new CheckerFactory();
		for (File file : fileList) {
			targetFile = file;
			lineNumber = 1;
			for (String fileType : config.fileTypes) {
				Checker checker = factory.createChecker(fileType, config.rules);
				checker.analyze(fileList, fileType, config.rules);
			}
		}

		// Report the results
		//File f = new File("C:\\Users\\niujij\\check_output.csv");
		if(results.size()==0) {
			System.out.println("Congrats! Your code style is cool!");
			System.exit(0);
		}
		File f = null;
		if(Constants.JENKINS.equals(workDir)) {
			f = new File("check_output.csv");
		}else {
			f = new File(System.getProperty("user.home")+"\\check_output.csv");
		}
		System.out.println("Report location:");
		System.out.println(f);
		if (f.exists())
			f.delete();
		f.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		PrintStream printStream = new PrintStream(fileOutputStream);
		System.setOut(printStream);
		for (Result result : results) {
			System.out.println(result.toString());
		}
	}
}
