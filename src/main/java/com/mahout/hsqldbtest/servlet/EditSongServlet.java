package com.mahout.hsqldbtest.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.SongService;

@Singleton
@WebServlet(value = "/editSong", loadOnStartup = 1)
public class EditSongServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Song song = new Song();

		if (req.getParameter("id") != null) {

			long songId = Long.parseLong(req.getParameter("id"));
			song = songService.getSong(songId);

			if (song != null) {

				if ("true".equals(req.getParameter("delete"))) {

					songService.deleteSong(songId);
					resp.sendRedirect(req.getContextPath() + "/searchSong");

				} else if ("true".equals(req.getParameter("update"))) {

					if (req.getParameter("filename") != null) {
						song.setFileName(req.getParameter("filename"));
					}
					song.setLyrics(req.getParameter("lyrics"));

					songService.updateSong(song);
					resp.sendRedirect(req.getContextPath() + "/searchSong");

				} else {
					req.setAttribute("song", song);
					req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/editSong.jsp").forward(req, resp);
				}

			} else {
				resp.sendRedirect(req.getContextPath() + "/searchSong");
			}

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
