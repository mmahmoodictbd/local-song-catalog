package com.mahout.hsqldbtest.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
@WebServlet(value = "/updatePlaylistsTag", loadOnStartup = 1)
public class UpdatePlaylistsTagServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String playlistsTags = req.getParameter("tags");

		if ("true".equals(req.getParameter("saveall"))) {

			String[] songIds = req.getParameter("songIds").split(",");

			if (playlistsTags != null && playlistsTags.length() > 0 && songIds.length > 0) {
				List<Song> songList = new ArrayList<Song>();
				for (String songId : songIds) {
					Song song = songService.getSong(Long.parseLong(songId));

					if (song.getPlayliststags() != null && song.getPlayliststags().length() > 0) {
						song.setPlayliststags(song.getPlayliststags() + "," + playlistsTags);
					} else {
						song.setPlayliststags(playlistsTags);
					}

					songList.add(song);
				}
				songService.updatePlaylistsTagsList(songList);
			}

			System.out.println(Arrays.toString(songIds) + " " + playlistsTags);

		} else {
			Long songId = Long.parseLong(req.getParameter("songId"));

			songService.updatePlaylistsTags(songId, playlistsTags);
		}

	}
}