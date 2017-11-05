package com.dxc.plm.codechecker.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utils {
	static Logger log = Logger.getLogger(Utils.class.getName());
	ApplicationConfiguration config = ApplicationConfiguration.getInstance();
	Properties messages = config.getMessages();

	public List<File> getFileListFromJenkins(String path) throws IOException {
		List<File> fileList = new ArrayList<>();
		File file = new File(Constants.CHANGE_LIST_FILE);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("D")) {
					continue;
				}

				line = path + "\\" + line.substring(1).trim();
				File f = new File(line);
				fileList.add(f);
				log.info(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader != null)
				bufferedReader.close();
			if(fileReader != null)
				fileReader.close();
		}
		return fileList;
	}

	public List<File> getFileList(String path) {
		File directory = new File(path);
		List<File> fileList = new ArrayList<>();
		if (directory.exists()) {
			LinkedList<File> directorylist = new LinkedList<>();
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					directorylist.add(file);
				} else {
					fileList.add(file);
				}
			}
			File tempFile;
			while (!directorylist.isEmpty()) {
				tempFile = directorylist.removeFirst();
				files = tempFile.listFiles();
				for (File file : files) {
					if (file.isDirectory()) {
						directorylist.add(file);
					} else {
						fileList.add(file);
					}
				}
			}
		} else {
			log.info(messages.getProperty("error.fileNotFound"));
		}
		return fileList;
	}

	public List<File> getFileList(String path, List<String> fileTypes) throws IOException {
		List<File> files = null;
		if (path.equals(Constants.JENKINS)) {
			files = getFileListFromJenkins(config.getJenkinsWorkspace());
		} else {
			files = getFileList(path);
		}

		List<File> qFiles = new ArrayList<>();
		for (File f : files) {
			String fileName = f.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(Constants.DOT) + 1);
			if (fileTypes.contains(suffix)) {
				qFiles.add(f);
			}
		}
		return qFiles;
	}

	public void deleteFile(String path) throws IOException {
		Files.delete(Paths.get(path));
	}

}
