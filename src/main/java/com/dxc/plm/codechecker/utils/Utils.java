package com.dxc.plm.codechecker.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Utils {

	public List<File> getFileList(String path) {
		File file = new File(path);
		List<File> fileList = new ArrayList<File>();
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					// System.out.println("文件夹:" + file2.getAbsolutePath());
					list.add(file2);
				} else {
					// System.out.println("文件:" + file2.getAbsolutePath());
					fileList.add(file2);
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						// System.out.println("文件夹:" + file2.getAbsolutePath());
						list.add(file2);
					} else {
						// System.out.println("文件:" + file2.getAbsolutePath());
						fileList.add(file2);
					}
				}
			}
		} else {
			// System.out.println("文件不存在!");
		}
		return fileList;
	}

	public List<File> getFileList(String path, List<String> fileTypes) {
        	List<File> files = getFileList(path);
        	List<File> qFiles = new ArrayList<File>();
        	for (File f : files) {
        		String fileName = f.getName();
        		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        		if(fileTypes.contains(suffix)) {
        			qFiles.add(f);
        		}
        	}
        	return qFiles;
        }
	
	
}
