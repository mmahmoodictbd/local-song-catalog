package com.mahout.hsqldbtest.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.mahout.hsqldbtest.bean.SearchParameters;
import com.mahout.hsqldbtest.model.Song;

@Singleton
public class SongService {

	private final DbService dbService;

	@Inject
	public SongService(DbService dbService) {
		this.dbService = dbService;
	}

	@Transactional
	public boolean isSongExistForMD5Update() {

		Long count = null;
		String sql = "SELECT COUNT(*) FROM Song WHERE infoMD5String IS NULL";

		try {
			count = ((Number) dbService.getQueryRunner().query(sql, new ScalarHandler<Object>(1))).longValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public List<Song> getSongListForMD5Update() {

		List<Song> songList = new ArrayList<Song>();
		String sql = "SELECT * FROM Song WHERE infoMD5String IS NULL";
		ResultSetHandler<List<Song>> rsh = new BeanListHandler<Song>(Song.class);

		try {
			songList = dbService.getQueryRunner().query(sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return songList;
	}

	@Transactional
	public Song getSongByInfoMD5String(String infoMD5String) {

		Song song = null;
		String sql = "select * from Song where infoMD5String = ? LIMIT 1";
		Object[] params = new Object[] { infoMD5String };
		ResultSetHandler<Song> rsh = new BeanHandler<Song>(Song.class);

		try {
			song = dbService.getQueryRunner().query(sql, rsh, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return song;
	}

	@Transactional
	public int[] updateSongMD5List(List<Song> songList) {

		int[] result = new int[0];

		if (songList.size() > 0) {

			int songListSize = songList.size();
			Object[][] paramObjects = new Object[songListSize][2];

			int counter = 0;
			for (Song song : songList) {
				paramObjects[counter][0] = song.getInfoMD5String();
				paramObjects[counter][1] = song.getId();
				counter++;
			}

			String sql = "UPDATE Song SET infoMD5String = ? WHERE id = ? ";
			try {
				result = dbService.getQueryRunner().batch(sql, paramObjects);
				System.out.println("DEBUG:: updateSongMD5List:result: " + Arrays.toString(result));
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

	@Transactional
	public void updateSong(Song song) {

		int result;
		String sql = "UPDATE Song SET fileName = ?, canonicalPath = ?, lastModified = ?, fileSize = ?, infoMD5String = ?, tags = ?, playliststags = ?, lyrics = ? WHERE id = ? ";
		try {
			result = dbService.getQueryRunner().update(
					sql,
					new Object[] { song.getFileName(), song.getCanonicalPath(), song.getLastModified(),
							song.getFileSize(), song.getInfoMD5String(), song.getTags(), song.getPlayliststags(),
							song.getLyrics(), song.getId() });
			System.out.println("DEBUG:: updateSongTags:result: " + result);
		} catch (SQLException ex) {
			System.out.println(ex);
		}

	}

	@Transactional
	public int saveSong(Song song) {

		List<Song> songList = new ArrayList<Song>();
		songList.add(song);
		int[] res = saveSongList(songList);
		return res[0];

	}

	@Transactional
	public int[] saveSongList(List<Song> songList) {

		int[] result = new int[0];

		if (songList.size() > 0) {

			System.out.println("DEBUG:: dbService:songList:" + songList.size());

			int songListSize = songList.size();
			Object[][] paramObjects = new Object[songListSize][9];

			int counter = 0;
			for (Song song : songList) {

				paramObjects[counter][0] = song.getId();
				paramObjects[counter][1] = song.getFileName();
				paramObjects[counter][2] = song.getCanonicalPath();
				paramObjects[counter][3] = song.getLastModified();
				paramObjects[counter][4] = song.getFileSize();
				paramObjects[counter][5] = song.getInfoMD5String();
				paramObjects[counter][6] = song.getTags();
				paramObjects[counter][7] = song.getPlayliststags();
				paramObjects[counter][8] = song.getLyrics();
				counter++;
			}

			String sql = "INSERT INTO Song (id, fileName, canonicalPath, lastModified, fileSize, infoMD5String, tags, playliststags, lyrics) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				result = dbService.getQueryRunner().batch(sql, paramObjects);
				System.out.println("DEBUG:: saveSongList:result: " + Arrays.toString(result));
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

	@Transactional
	public void deleteSong(Long songId) {

		int result = 0;
		String sql = "DELETE FROM Song WHERE id = ? ";

		try {
			result = dbService.getQueryRunner().update(sql, new Object[] { songId });
			System.out.println("DEBUG:: delteSong:result: " + result);
		} catch (SQLException ex) {
			System.out.println(ex);
		}

	}

	@Transactional
	public int[] deleteSongList(List<Song> deleteSongList) {

		int[] result = new int[0];

		if (deleteSongList.size() > 0) {

			int songListSize = deleteSongList.size();
			Object[][] paramObjects = new Object[songListSize][1];

			int counter = 0;
			for (Song song : deleteSongList) {
				paramObjects[counter][0] = song.getId();
				counter++;
			}

			String sql = "DELETE FROM Song WHERE id = ? ";
			try {
				result = dbService.getQueryRunner().batch(sql, paramObjects);
				System.out.println("DEBUG:: deleteSongList:result: " + Arrays.toString(result));
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

	public List<Song> getNonPersistedSongList(List<Song> diskSongList, List<Song> persistedSongList) {

		Map<String, Song> persistedSongMap = new HashMap<String, Song>();
		for (Song song : persistedSongList) {
			persistedSongMap.put(song.getCanonicalPath(), song);
		}

		List<Song> nonPersistedSongList = new ArrayList<Song>();
		for (Song song : diskSongList) {
			if (persistedSongMap.get(song.getCanonicalPath()) == null) {
				nonPersistedSongList.add(song);
			}
		}

		return nonPersistedSongList;
	}

	@Transactional
	public void updateSongTags(Long songId, String tags) {

		if (tags != null && tags.trim().length() == 0) {
			tags = null;
		}

		int result;
		String sql = "UPDATE Song SET tags = ? WHERE id = ? ";
		try {
			result = dbService.getQueryRunner().update(sql, new Object[] { tags, songId });
			System.out.println("DEBUG:: updateSongTags:result: " + result);
		} catch (SQLException ex) {
			System.out.println(ex);
		}

	}

	@Transactional
	public void updatePlaylistsTags(Long songId, String tags) {

		int result;
		String sql = "UPDATE Song SET playliststags = ? WHERE id = ? ";
		try {
			result = dbService.getQueryRunner().update(sql, new Object[] { tags, songId });
			System.out.println("DEBUG:: updatePlaylistsTags:result: " + result);
		} catch (SQLException ex) {
			System.out.println(ex);
		}

	}

	@Transactional
	public int[] updateSongTagsList(List<Song> songList) {

		int[] result = new int[0];

		if (songList.size() > 0) {

			int songListSize = songList.size();
			Object[][] paramObjects = new Object[songListSize][2];

			int counter = 0;
			for (Song song : songList) {
				paramObjects[counter][0] = song.getTags();
				paramObjects[counter][1] = song.getId();
				counter++;
			}

			String sql = "UPDATE Song SET tags = ? WHERE id = ? ";
			try {
				result = dbService.getQueryRunner().batch(sql, paramObjects);
				System.out.println("DEBUG:: updateSongTagsList:result: " + Arrays.toString(result));
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

	@Transactional
	public int[] updatePlaylistsTagsList(List<Song> songList) {

		int[] result = new int[0];

		if (songList.size() > 0) {

			int songListSize = songList.size();
			Object[][] paramObjects = new Object[songListSize][2];

			int counter = 0;
			for (Song song : songList) {
				paramObjects[counter][0] = song.getPlayliststags();
				paramObjects[counter][1] = song.getId();
				counter++;
			}

			String sql = "UPDATE Song SET playliststags = ? WHERE id = ? ";
			try {
				result = dbService.getQueryRunner().batch(sql, paramObjects);
				System.out.println("DEBUG:: updatePlaylistsTagsList:result: " + Arrays.toString(result));
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

	@Transactional
	public List<String> getUniqueTagList() {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		String sql = "SELECT tags FROM Song ORDER BY id";
		try {
			mapList = dbService.getQueryRunner().query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		SortedSet<String> uniqueTagSet = new TreeSet<String>();
		for (Map<String, Object> map : mapList) {
			String tags = (String) map.get("tags");
			if (tags != null && tags.length() > 0) {
				String[] tagArr = tags.split(",");
				Collections.addAll(uniqueTagSet, tagArr);
			}

		}

		return new ArrayList<String>(uniqueTagSet);
	}

	@Transactional
	public List<String> getUniquePlaylistTagList() {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		String sql = "SELECT playliststags FROM Song ORDER BY id";
		try {
			mapList = dbService.getQueryRunner().query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Set<String> uniqueTagSet = new HashSet<String>();
		for (Map<String, Object> map : mapList) {
			String tags = (String) map.get("playliststags");
			if (tags != null && tags.length() > 0) {
				String[] tagArr = tags.split(",");
				Collections.addAll(uniqueTagSet, tagArr);
			}

		}

		return new ArrayList<String>(uniqueTagSet);
	}

	@Transactional
	public List<Map<String, Object>> getResultSetMapList(String query, String param[]) {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		try {
			mapList = dbService.getQueryRunner().query(query, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mapList;
	}

	@Transactional
	public Song getSong(Long songId) {

		Song song = null;
		String sql = "select * from Song WHERE id = ? ORDER BY id LIMIT 1";
		ResultSetHandler<Song> rsh = new BeanHandler<Song>(Song.class);

		try {
			song = dbService.getQueryRunner().query(sql, rsh, new Object[] { songId });
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return song;
	}

	@Transactional
	public List<Song> getSongListByPath(String path) {

		List<Song> songList = new ArrayList<Song>();
		String sql = "select * from Song WHERE LCASE(canonicalPath) like ? ORDER BY id LIMIT 30";
		ResultSetHandler<List<Song>> rsh = new BeanListHandler<Song>(Song.class);

		try {
			songList = dbService.getQueryRunner().query(sql, rsh, new Object[] { path.toLowerCase() + "%" });
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return songList;

	}

	@Transactional
	public Song getSongByCanonicalPath(String canonicalPath) {

		Song song = null;
		String sql = "select * from Song WHERE canonicalPath like ? ";
		ResultSetHandler<Song> rsh = new BeanHandler<Song>(Song.class);

		try {
			song = dbService.getQueryRunner().query(sql, rsh, new Object[] { canonicalPath });
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return song;

	}

	@Transactional
	public List<Song> getSongList(boolean onlyTagged) {

		String sql = "";
		if (onlyTagged) {
			sql = "select * from Song WHERE tags IS NULL ORDER BY id LIMIT 30";
		} else {
			sql = "select * from Song ORDER BY id LIMIT 30";
		}
		System.out.println(sql);
		ResultSetHandler<List<Song>> rsh = new BeanListHandler<Song>(Song.class);
		List<Song> songList = new ArrayList<Song>();
		try {
			songList = dbService.getQueryRunner().query(sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return songList;
	}

	@Transactional
	public List<Song> getSongList(SearchParameters params) {

		String searchString = params.getSearchString();
		boolean onlyUntagged = params.isSearchOnlyUntagged();
		boolean excludeFileName = params.isSearchExcludeFileName();
		boolean excludeTags = params.isSearchExcludeTags();
		boolean excludePlaylists = params.isSearchExcludePlaylists();
		boolean excludeFilePath = params.isSearchExcludeFilePath();
		boolean excludeLyrics = params.isSearchExcludeLyrics();

		if ((searchString == null || searchString.trim().length() == 0) && !excludeFileName && !excludeTags
				&& !excludePlaylists && !excludeFilePath && !excludeLyrics) {
			return getSongList(onlyUntagged);

		} else {

			String[] searchQueryToken = searchString.toLowerCase().trim().split(" ");
			for (int i = 0; i < searchQueryToken.length; i++) {
				searchQueryToken[i] = "%" + searchQueryToken[i] + "%";
			}

			String sqlCommonPart = "";
			int countSearchField = 0;

			if (!excludeFileName) {
				sqlCommonPart += "LCASE(filename) like ? OR ";
				countSearchField++;
			}
			if (!excludeTags) {
				sqlCommonPart += "LCASE(tags) like ? OR ";
				countSearchField++;
			}
			if (!excludePlaylists) {
				sqlCommonPart += "LCASE(playliststags) like ? OR ";
				countSearchField++;
			}
			if (!excludeFilePath) {
				sqlCommonPart += "LCASE(canonicalPath) like ? OR ";
				countSearchField++;
			}
			if (!excludeLyrics) {
				sqlCommonPart += "LCASE(lyrics) like ? OR ";
				countSearchField++;
			}

			int lastIndexOfOR = sqlCommonPart.lastIndexOf("OR");
			if (lastIndexOfOR > -1) {
				sqlCommonPart = sqlCommonPart.substring(0, lastIndexOfOR);
			} else {
				sqlCommonPart = "1!=1";
			}

			sqlCommonPart = " (" + sqlCommonPart + ") ";

			Object[] paramObjects = new Object[searchQueryToken.length * countSearchField];
			for (int i = 0, j = 0; i < paramObjects.length; i += countSearchField, j++) {
				for (int k = 0; k < countSearchField; k++) {
					paramObjects[i + k] = searchQueryToken[j];
				}
			}

			String sql = "Select * From Song Where ( ";
			for (int i = 0; i < searchQueryToken.length; i++) {
				sql += sqlCommonPart;
				if (i != searchQueryToken.length - 1) {
					sql += " OR ";
				}
			}
			sql += " ) ";

			if (onlyUntagged) {
				sql += " AND tags IS NULL";
			}
			sql += " ORDER BY id LIMIT 30";

			System.out.println(sql);

			List<Song> songList = new ArrayList<Song>();
			ResultSetHandler<List<Song>> rsh = new BeanListHandler<Song>(Song.class);

			try {
				songList = dbService.getQueryRunner().query(sql, rsh, paramObjects);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return songList;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Song> searchSongFromDisk(String searchPath) {

		List<Song> songList = new ArrayList<Song>();

		File dir = new File(searchPath);

		if (dir != null && dir.isDirectory()) {
			IOFileFilter fileFilter = new WildcardFileFilter("*.mp3");

			List<File> mp3FileList = null;
			mp3FileList = (List<File>) FileUtils.listFiles(dir, fileFilter, TrueFileFilter.TRUE);

			for (File file : mp3FileList) {

				Song song = new Song();
				song.setFileName(file.getName());

				try {
					song.setCanonicalPath(file.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}

				song.setLastModified(file.lastModified());
				song.setFileSize(file.length());
				songList.add(song);
			}

		} else {
			System.out.println("DEBUG:: SearchPath:" + searchPath + " is not a directory!");
		}

		return songList;
	}

	public Map<String, Object> getDashboardInfo() {

		Map<String, Object> dashboardInfoMap = new HashMap<String, Object>();

		String sql = "SELECT COUNT(s1.id) totalSong, COUNT(s2.id) untaggedSong, "
				+ "(COUNT(s1.id)-COUNT(s2.id)) taggedSong FROM Song s1 "
				+ "LEFT JOIN Song s2 on (s1.id = s2.id and s2.tags IS NULL) ";

		String sql1 = "SELECT COUNT(s1.id) totalOrphanedSong FROM OrphanedSong s1 ";

		try {
			dashboardInfoMap.putAll(dbService.getQueryRunner().query(sql, new MapHandler()));
			dashboardInfoMap.putAll(dbService.getQueryRunner().query(sql1, new MapHandler()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dashboardInfoMap;
	}

	public List<String> getCanonicalPathList() {

		List<String> canonicalPathList = new ArrayList<String>();
		String sql = "SELECT canonicalPath FROM Song";

		try {
			canonicalPathList = dbService.getQueryRunner().query(sql, new ColumnListHandler<String>());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return canonicalPathList;
	}

	@Transactional
	public List<Song> getOrphanedSongList() {

		String sql = "select * from OrphanedSong ORDER BY id LIMIT 300";

		ResultSetHandler<List<Song>> rsh = new BeanListHandler<Song>(Song.class);
		List<Song> orphanedSongList = new ArrayList<Song>();
		try {
			orphanedSongList = dbService.getQueryRunner().query(sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orphanedSongList;
	}

	@Transactional
	public int[] saveOrphanedSongList(List<Song> songList) {

		int[] result = new int[0];

		if (songList.size() > 0) {

			int songListSize = songList.size();
			Object[][] paramObjects = new Object[songListSize][9];

			int counter = 0;
			for (Song song : songList) {

				paramObjects[counter][0] = song.getId();
				paramObjects[counter][1] = song.getFileName();
				paramObjects[counter][2] = song.getCanonicalPath();
				paramObjects[counter][3] = song.getLastModified();
				paramObjects[counter][4] = song.getFileSize();
				paramObjects[counter][5] = song.getInfoMD5String();
				paramObjects[counter][6] = song.getTags();
				paramObjects[counter][7] = song.getPlayliststags();
				paramObjects[counter][8] = song.getLyrics();
				counter++;
			}

			String sql = "INSERT INTO OrphanedSong (id, fileName, canonicalPath, lastModified, fileSize, infoMD5String, tags, playliststags, lyrics) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				result = dbService.getQueryRunner().batch(sql, paramObjects);
				System.out.println("DEBUG:: saveOrphanedSongList:result: " + Arrays.toString(result));
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

	@Transactional
	public void deleteOrphanedSong(Long songId) {

		int result = 0;
		String sql = "DELETE FROM OrphanedSong WHERE id = ? ";

		try {
			result = dbService.getQueryRunner().update(sql, new Object[] { songId });
			System.out.println("DEBUG:: deleteOrphanedSong:result: " + result);
		} catch (SQLException ex) {
			System.out.println(ex);
		}

	}

	@Transactional
	public boolean isRestoreOrphanedSongPossible() {

		Long count = null;
		String sql = "SELECT COUNT(*) FROM OrphanedSong os " + "JOIN Song so on (so.infoMD5String = os.infoMD5String)";

		try {
			count = ((Number) dbService.getQueryRunner().query(sql, new ScalarHandler<Object>(1))).longValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public Song getOrphanedSongByInfoMD5String(String infoMD5String) {

		Song song = null;
		String sql = "select * from OrphanedSong where infoMD5String = ? LIMIT 1";
		Object[] params = new Object[] { infoMD5String };
		ResultSetHandler<Song> rsh = new BeanHandler<Song>(Song.class);

		try {
			song = dbService.getQueryRunner().query(sql, rsh, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return song;
	}

	@Transactional
	public List<String> restorableSongInfoMD5StringList() {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<String> restorableSongInfoMD5StringList = new ArrayList<String>();

		String sql = "SELECT so.infoMD5String FROM OrphanedSong os "
				+ "JOIN Song so on (so.infoMD5String = os.infoMD5String)";

		try {
			mapList = dbService.getQueryRunner().query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Map<String, Object> map : mapList) {
			restorableSongInfoMD5StringList.add((String) map.get("infoMD5String"));
		}

		return restorableSongInfoMD5StringList;
	}

	public List<String> getSearchSpace(List<String> pathList) {

		SortedSet<String> newPathSet = new TreeSet<String>();
		SortedSet<String> pathSet = new TreeSet<String>(pathList);
		SortedSet<String> removablePathSet = new TreeSet<String>();

		for (String path : pathSet) {
			int lastIndexOfSeparator = path.lastIndexOf(File.separatorChar);
			if (lastIndexOfSeparator > -1) {
				newPathSet.add(path.substring(0, lastIndexOfSeparator));
			}
		}

		for (String path : newPathSet) {
			for (String path1 : newPathSet) {
				if (path1.startsWith(path) && !path1.equals(path)) {
					removablePathSet.add(path1);
				}
			}
		}

		newPathSet.removeAll(removablePathSet);

		if (pathSet.size() != newPathSet.size()) {
			return getSearchSpace(new ArrayList<String>(newPathSet));
		} else {
			return new ArrayList<String>(pathSet);
		}

	}
}
