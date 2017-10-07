package com.dxc.plm.codechecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dxc.plm.codechecker.config.CheckerConfiguration;
import com.dxc.plm.codechecker.model.Result;
import com.dxc.plm.codechecker.utils.Utils;

/**
 * 
 * @author Robin
 * @date 01/10/2017
 * @version 1.0
 */
public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());
	public static File targetFile;
	public static int lineNumber;
	public static List<Result> results = new ArrayList<Result>();

	public static void main(String[] args) throws Exception {

		// load configuration
		CheckerConfiguration config = new CheckerConfiguration();
		Utils utils = new Utils();
		String workDir = "C:\\Users\\niujij\\workspace\\svnWorkspace\\mainline\\developer\\scripts";
		if(args.length>0) {
			workDir = args[0];
		}
		
		List<File> fileList = utils.getFileList(workDir, config.getFileTypes());

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
		File f = new File("C:\\Users\\niujij\\check_output.csv");
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
