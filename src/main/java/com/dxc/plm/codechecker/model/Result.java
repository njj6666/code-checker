package com.dxc.plm.codechecker.model;

import java.io.File;

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
		return file + "\t" + lineNumber + "\t" + line + "\t" + message;
	}

}
