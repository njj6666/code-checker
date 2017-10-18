package com.dxc.plm.codechecker;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;

import java.security.MessageDigest;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;

public class DirDiff {

	/**
	 * 
	 * 获取单个文件的MD5值！
	 *
	 * 
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 */

	public static String getFileMD5(File file) {

		if (!file.isFile()) {

			return null;

		}

		MessageDigest digest = null;

		FileInputStream in = null;

		byte buffer[] = new byte[1024];

		int len;

		try {

			digest = MessageDigest.getInstance("MD5");

			in = new FileInputStream(file);

			while ((len = in.read(buffer, 0, 1024)) != -1) {

				digest.update(buffer, 0, len);

			}

			in.close();

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}

		BigInteger bigInt = new BigInteger(1, digest.digest());

		return bigInt.toString(16);

	}

	/**
	 * 
	 * 获取文件夹中文件的MD5值
	 *
	 * 
	 * 
	 * @param file
	 * 
	 * @param listChild
	 * 
	 *            ;true递归子目录中的文件
	 * 
	 * @return
	 * 
	 */

	public static Map<String, String> getDirMD5(File file, boolean listChild) {

		if (!file.isDirectory()) {

			return null;

		}

		Map<String, String> map = new HashMap<String, String>();

		String md5;

		File files[] = file.listFiles();

		for (int i = 0; i < files.length; i++) {

			File f = files[i];

			if (f.isDirectory() && listChild) {

				map.putAll(getDirMD5(f, listChild));

			} else {

				md5 = getFileMD5(f);

				if (md5 != null) {

					map.put(f.getPath(), md5);

				}

			}

		}

		return map;

	}

	public static void main(String[] args) throws IOException {

		String dxc_dir = "C:\\Users\\niujij\\workspace\\PG_Project\\tags\\rc171016\\src";

		String pg_dir = "C:\\Users\\niujij\\Documents\\PG\\release_process\\src";

		Map<String, String> map = getDirMD5(new File(dxc_dir), true);

		Map<String, String> map2 = getDirMD5(new File(pg_dir), true);

		Iterator<String> it = map.keySet().iterator();
		
		File newFiles = new File("new_files.txt");
		File modFiles = new File("modified_files.txt");
		File delFiles = new File("del_or_nil_files.txt");
		
		if (newFiles.exists())
			newFiles.delete();
		newFiles.createNewFile();
		FileOutputStream newFilesOutputStream = new FileOutputStream(newFiles);
		PrintStream newFilesPrintStream = new PrintStream(newFilesOutputStream);
		
		if (modFiles.exists())
			modFiles.delete();
		modFiles.createNewFile();
		FileOutputStream modFilesOutputStream = new FileOutputStream(modFiles);
		PrintStream modFilesPrintStream = new PrintStream(modFilesOutputStream);
		
		if (delFiles.exists())
			delFiles.delete();
		delFiles.createNewFile();
		FileOutputStream delFilesOutputStream = new FileOutputStream(delFiles);
		PrintStream delFilesPrintStream = new PrintStream(delFilesOutputStream);

		while (it.hasNext()) {

			String key = it.next();

			String key2 = key.replace(dxc_dir, pg_dir);

			String value = map.get(key);

			String value2 = map2.remove(key2);

			if (value2 == null) {
				System.setOut(newFilesPrintStream);
				//System.out.println(key + " -> " + value + " || 文件不存在");
				System.out.println(key);

			} else if (!value.equals(value2)) {
				System.setOut(modFilesPrintStream);
				System.out.println(key);
			}

		}

		it = map2.keySet().iterator();
		System.setOut(delFilesPrintStream);
		while (it.hasNext()) {
			String key = it.next();
			if(!key.endsWith(".bak")) {
				System.out.println(key);
				//System.out.println("文件不存在 || " + key + " -> " + map2.get(key));
			}
		}

	}

}