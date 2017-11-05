package com.dxc.plm.codechecker.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dxc.plm.codechecker.model.Result;

public class ApplicationContext {
	private static File targetFile = null;
	private static int lineNumber = 0;
	private static List<Result> results = new ArrayList<>();
	
	private ApplicationContext() {}

	public static File getTargetFile() {
		return targetFile;
	}

	public static void setTargetFile(File targetFile) {
		ApplicationContext.targetFile = targetFile;
	}

	public static int getLineNumber() {
		return lineNumber;
	}

	public static void setLineNumber(int lineNumber) {
		ApplicationContext.lineNumber = lineNumber;
	}

	public static List<Result> getResults() {
		return results;
	}

	public static void setResults(List<Result> results) {
		ApplicationContext.results = results;
	}

}
