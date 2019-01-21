package com.mahout.hsqldbtest.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.List;

public class Utils {

	public static String fileNamePrettifier(String fileName) {

		try {
			int dotIndex = fileName.lastIndexOf(".mp3");
			if (dotIndex > -1) {
				fileName = fileName.substring(0, dotIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}

	public static String filePathPrettifier(String filePath) {

		try {
			int slashIndex = filePath.lastIndexOf(File.separatorChar);
			if (slashIndex > -1) {
				filePath = filePath.substring(0, slashIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;
	}

	public static String listToJSON(List<String> list) {

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("[");
		for (String string : list) {
			sBuffer.append("\"" + string + "\",");
		}
		if (sBuffer.charAt(sBuffer.length() - 1) == ',') {
			sBuffer.deleteCharAt(sBuffer.length() - 1);
		}
		sBuffer.append("]");

		return sBuffer.toString();
	}

	@SuppressWarnings("resource")
	public static String getMD5String(String str) {

		Formatter formatter = new Formatter();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			byte hash[] = md.digest();
			formatter = new Formatter();
			for (byte b : hash) {
				formatter.format("%02x", b);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return formatter.toString();
	}

	@SuppressWarnings("resource")
	public static String getMD5String(File file) {

		Formatter formatter = new Formatter();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DigestInputStream dis = new DigestInputStream(bis, md);

			while (dis.read() != -1)
				;
			byte[] hash = md.digest();
			formatter = new Formatter();
			for (byte b : hash) {
				formatter.format("%02x", b);
			}
		} catch (Exception e) {

		}
		return formatter.toString();

	}
}
