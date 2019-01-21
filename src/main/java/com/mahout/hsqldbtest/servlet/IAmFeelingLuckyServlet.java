package com.mahout.hsqldbtest.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.SongService;
import com.mahout.hsqldbtest.util.Utils;

@Singleton
@WebServlet(value = "/iAmFeelingLucky", loadOnStartup = 1)
public class IAmFeelingLuckyServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Song> orphanedSongList = songService.getOrphanedSongList();
		List<String> orphanedSongNameList = new ArrayList<String>();
		for (Song orphanedSong : orphanedSongList) {
			orphanedSongNameList.add(orphanedSong.getOriginalFileName());
		}

		List<String> songPathList = new ArrayList<String>();
		for (String path : songService.getCanonicalPathList()) {
			int lastIndexOfSeparator = path.lastIndexOf(File.separatorChar);
			if (lastIndexOfSeparator > -1) {
				songPathList.add(path.substring(0, lastIndexOfSeparator));
			}
		}

		NameFileFilter fileFilter = new NameFileFilter(orphanedSongNameList);
		List<String> searchSpaceList = songService.getSearchSpace(songPathList);
		System.out.println("searchSpace:" + searchSpaceList);

		List<File> orphanedFileList = new ArrayList<File>();
		for (String dirStr : searchSpaceList) {
			File dir = new File(dirStr);
			orphanedFileList.addAll(FileUtils.listFiles(dir, fileFilter, TrueFileFilter.TRUE));
		}

		Map<String, Song> orphanedSongMap = new HashMap<String, Song>();
		for (Song song : orphanedSongList) {
			orphanedSongMap.put(song.getOriginalFileName(), song);
		}

		int restoredSongCount = 0;
		for (File file : orphanedFileList) {
			Song song = orphanedSongMap.get(file.getName());

			if (song.getFileSize() == file.length()) {

				String md5 = Utils.getMD5String(file);
				if (md5 != null && md5.equals(song.getInfoMD5String())) {
					song.setId(null);
					song.setCanonicalPath(file.getCanonicalPath());
					songService.saveSong(song);
					songService.deleteOrphanedSong(song.getId());
					restoredSongCount++;
					System.out.println("DEBUG:: Orphaned song - " + song.getFileName() + " path updated.");
				} else {
				}
			} else {
			}
		}
		resp.getWriter().write(restoredSongCount);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
