package com.dxc.plm.codechecker.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dxc.plm.codechecker.model.Result;
@Component
public class GlobalVar {
	private static File targetFile = null;
	private static int lineNumber = 0;
	private static List<Result> results = new ArrayList<>();
	
	private GlobalVar() {}

	public static File getTargetFile() {
		return targetFile;
	}

	public static void setTargetFile(File targetFile) {
		GlobalVar.targetFile = targetFile;
	}

	public static int getLineNumber() {
		return lineNumber;
	}

	public static void setLineNumber(int lineNumber) {
		GlobalVar.lineNumber = lineNumber;
	}

	public static List<Result> getResults() {
		return results;
	}

	public static void setResults(List<Result> results) {
		GlobalVar.results = results;
	}

}
