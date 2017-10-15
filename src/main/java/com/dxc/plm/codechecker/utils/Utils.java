package com.dxc.plm.codechecker.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dxc.plm.codechecker.config.CheckerConfiguration;

public class Utils {
	CheckerConfiguration config = CheckerConfiguration.getInstance(null);
	
	public static void main(String args[]) {
		Utils utils = new Utils();
		List<File> fileList = utils.getFileListFromJenkins("C:\\Program Files (x86)\\Jenkins\\workspace\\svnTutorial");
		System.out.println(fileList.size());
	}
	
	public List<File> getFileListFromJenkins(String path){
		List<File> fileList = new ArrayList<File>();
		try {
			File file = new File("changelist.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.startsWith("D")) {
					continue;
				}
				
				line = path+"\\"+line.substring(1).trim();
				File f = new File(line);
				fileList.add(f);
				System.out.println(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileList;
	}

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
		List<File> files = null;
		if(path.equals(Constants.JENKINS)) {
			files = getFileListFromJenkins(config.getJenkins_workspace());
		}else {
			files = getFileList(path);
		}
        	
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
