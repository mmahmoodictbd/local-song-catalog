package com.mahout.hsqldbtest.bean;

import java.util.List;

import com.mahout.hsqldbtest.model.Song;

public class SearchResultBean {

	private String searchString;
	private String tagList;
	private String playlistsTagList;

	private boolean searchOnlyUntagged;

	private boolean searchExcludeFileName;
	private boolean searchExcludeTags;
	private boolean searchExcludePlaylists;
	private boolean searchExcludeFilePath;
	private boolean searchExcludeLyrics;

	private List<Song> songList;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getTagList() {
		return tagList;
	}

	public void setTagList(String tagList) {
		this.tagList = tagList;
	}

	public String getPlaylistsTagList() {
		return playlistsTagList;
	}

	public void setPlaylistsTagList(String playlistsTagList) {
		this.playlistsTagList = playlistsTagList;
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

	public List<Song> getSongList() {
		return songList;
	}

	public void setSongList(List<Song> songList) {
		this.songList = songList;
	}

	@Override
	public String toString() {
		return "SearchResultBean [searchString=" + searchString + ", tagList=" + tagList + ", playlistsTagList="
				+ playlistsTagList + ", searchOnlyUntagged=" + searchOnlyUntagged + ", searchExcludeFileName="
				+ searchExcludeFileName + ", searchExcludeTags=" + searchExcludeTags + ", searchExcludePlaylists="
				+ searchExcludePlaylists + ", searchExcludeFilePath=" + searchExcludeFilePath
				+ ", searchExcludeLyrics=" + searchExcludeLyrics + ", songList=" + songList + "]";
	}

}
