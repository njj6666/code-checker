package com.dxc.plm.codechecker.model;

import java.io.File;

import com.dxc.plm.codechecker.utils.Constants;

public class Result {
	private int lineNumber;
	private String message;
	private File file;
	private String line;

	public Result(int lineNumber, String message, File file, String line) {

		this.lineNumber = lineNumber;
		this.message = message;
		this.file = file;
		this.line = line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public String toString() {
		// delimiter by 6 hyphens
		return file.getName() + Constants.RESULT_DELIMER + lineNumber + Constants.RESULT_DELIMER + message
				+ Constants.RESULT_DELIMER + line;
	}

}
