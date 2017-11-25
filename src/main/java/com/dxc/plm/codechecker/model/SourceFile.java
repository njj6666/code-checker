package com.dxc.plm.codechecker.model;

import java.util.List;

public class SourceFile {
	private String fileName;
	private String filePath;
	private int countOfLines;
	private String fileType;
	private String fileExtension;
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	private List<CodeLine> lines;

	public String getFileName() {
		return fileName;
	}

	public List<CodeLine> getLines() {
		return lines;
	}

	public void setLines(List<CodeLine> lines) {
		this.lines = lines;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getLineNumber() {
		return countOfLines;
	}

	public void setLineNumber(int lineNumber) {
		this.countOfLines = lineNumber;
	}

}
