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
@WebServlet(value = "/orphanedSongList", loadOnStartup = 1)
public class OrphanedSongListServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("delete") != null) {
			Long songId = Long.parseLong(req.getParameter("delete"));
			songService.deleteOrphanedSong(songId);
		}

		List<Song> orphanedSongList = songService.getOrphanedSongList();

		req.setAttribute("orphanedSongList", orphanedSongList);
		req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/orphanedSongList.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
