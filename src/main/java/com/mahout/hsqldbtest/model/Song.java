package com.mahout.hsqldbtest.model;

import java.io.File;

import com.mahout.hsqldbtest.util.Utils;

public class Song {

	private Long id;
	private String fileName;
	private String canonicalPath;
	private Long lastModified;
	private Long fileSize;
	private String infoMD5String;
	private String tags;
	private String playliststags;
	private String lyrics;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCanonicalPath() {
		return canonicalPath;
	}

	public void setCanonicalPath(String canonicalPath) {
		this.canonicalPath = canonicalPath;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getInfoMD5String() {
		return infoMD5String;
	}

	public void setInfoMD5String(String infoMD5String) {
		this.infoMD5String = infoMD5String;
	}

	public String getPlayliststags() {
		return playliststags;
	}

	public void setPlayliststags(String playliststags) {
		this.playliststags = playliststags;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public String getOriginalFileName() {

		if (canonicalPath != null && canonicalPath.length() > 0) {
			return canonicalPath.substring(canonicalPath.lastIndexOf(File.separatorChar) + 1);
		} else {
			return fileName;
		}
	}

	public String getPrettifiedCanonicalPath() {
		return Utils.filePathPrettifier(canonicalPath);
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", fileName=" + fileName + ", canonicalPath=" + canonicalPath + ", lastModified="
				+ lastModified + ", fileSize=" + fileSize + ", infoMD5String=" + infoMD5String + ", tags=" + tags
				+ ", playliststags=" + playliststags + ", lyrics=" + lyrics + "]";
	}

}
