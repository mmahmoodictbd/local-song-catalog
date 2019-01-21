package com.mahout.hsqldbtest.bean;

public class SearchParameters {

	private String searchString;
	private boolean searchOnlyUntagged;
	private boolean searchExcludeFileName;
	private boolean searchExcludeTags;
	private boolean searchExcludePlaylists;
	private boolean searchExcludeFilePath;
	private boolean searchExcludeLyrics;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public boolean isSearchOnlyUntagged() {
		return searchOnlyUntagged;
	}

	public void setSearchOnlyUntagged(boolean searchOnlyUntagged) {
		this.searchOnlyUntagged = searchOnlyUntagged;
	}

	public boolean isSearchExcludeFileName() {
		return searchExcludeFileName;
	}

	public void setSearchExcludeFileName(boolean searchExcludeFileName) {
		this.searchExcludeFileName = searchExcludeFileName;
	}

	public boolean isSearchExcludeTags() {
		return searchExcludeTags;
	}

	public void setSearchExcludeTags(boolean searchExcludeTags) {
		this.searchExcludeTags = searchExcludeTags;
	}

	public boolean isSearchExcludePlaylists() {
		return searchExcludePlaylists;
	}

	public void setSearchExcludePlaylists(boolean searchExcludePlaylists) {
		this.searchExcludePlaylists = searchExcludePlaylists;
	}

	public boolean isSearchExcludeFilePath() {
		return searchExcludeFilePath;
	}

	public void setSearchExcludeFilePath(boolean searchExcludeFilePath) {
		this.searchExcludeFilePath = searchExcludeFilePath;
	}

	public boolean isSearchExcludeLyrics() {
		return searchExcludeLyrics;
	}

	public void setSearchExcludeLyrics(boolean searchExcludeLyrics) {
		this.searchExcludeLyrics = searchExcludeLyrics;
	}

}
