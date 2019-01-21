package com.mahout.hsqldbtest.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.SongService;

@Singleton
@WebServlet(value = "/searchDiskMp3", loadOnStartup = 1)
public class SearchDiskMp3Servlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// System.out.println(FileUtils.c);

		String diskMp3Path = request.getParameter("diskMp3Path");
		request.setAttribute("diskMp3Path", diskMp3Path);

		if (diskMp3Path != null && diskMp3Path.trim().length() > 0) {

			List<Song> persistedSongList = songService.getSongListByPath(diskMp3Path);
			List<Song> songListOnDisk = songService.searchSongFromDisk(diskMp3Path);
			List<Song> nonPersistedSongList = songService.getNonPersistedSongList(songListOnDisk, persistedSongList);

			songService.saveSongList(nonPersistedSongList);

			String searchSuccessMsg = "";
			if (songListOnDisk.size() == 0) {
				searchSuccessMsg = "No mp3 song found on - " + diskMp3Path;
			} else if (songListOnDisk.size() > 0 && nonPersistedSongList.size() == 0) {
				searchSuccessMsg = "All mp3 song already added.";
			} else if (songListOnDisk.size() > 0 && nonPersistedSongList.size() > 0
					&& songListOnDisk.size() == nonPersistedSongList.size()) {
				searchSuccessMsg = nonPersistedSongList.size() + " mp3 song added.";
			} else if (songListOnDisk.size() > 0 && nonPersistedSongList.size() > 0
					&& songListOnDisk.size() != nonPersistedSongList.size()) {
				searchSuccessMsg = nonPersistedSongList.size() + " mp3 song newly added.";
			}

			request.setAttribute("nonPersistedSongList", nonPersistedSongList);
			request.setAttribute("nonPersistedSongListSize", nonPersistedSongList.size());
			request.setAttribute("searchSuccess", true);
			request.setAttribute("searchSuccessMsg", searchSuccessMsg);
		}

		request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/searchSongOnDisk.jsp")
				.forward(request, response);
	}
}