package com.dxc.plm.codechecker.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Report {
	//targetFile and lineNumber放在Report中并不合适。它们是两个全局变量，用来存储中间变量。
	private static File targetFile = null;
	private static int lineNumber = 0;
	private static List<ReportItem> reportItems = new ArrayList<>();
	private static String reportName;
	
	public static void addReportItem(ReportItem item) {
		Report.getReportItems().add(item);
	}
	
	public static String getReportName() {
		return reportName;
	}

	public static void setReportName(String reportName) {
		Report.reportName = reportName;
	}

	private Report() {}

	public static File getTargetFile() {
		return targetFile;
	}

	public static void setTargetFile(File targetFile) {
		Report.targetFile = targetFile;
	}

	public static int getLineNumber() {
		return lineNumber;
	}

	public static void setLineNumber(int lineNumber) {
		Report.lineNumber = lineNumber;
	}

	public static List<ReportItem> getReportItems() {
		return reportItems;
	}

	public static void setReportItems(List<ReportItem> results) {
		Report.reportItems = results;
	}
}
