package com.mahout.hsqldbtest.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
@WebServlet(value = "/tags", loadOnStartup = 1)
public class TagsServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("delete") != null) {
			String deleteTag = req.getParameter("delete");

			List<Song> songList = songService.getSongList(false);
			List<Song> songListForUpdate = new ArrayList<Song>();

			for (Song song : songList) {
				if (song.getTags() != null && song.getTags().contains(deleteTag)) {

					List<String> tagsList = new LinkedList<String>(Arrays.asList(song.getTags().split(",")));
					int tagIndex = tagsList.indexOf(deleteTag);

					if (tagIndex > -1) {
						tagsList.remove(tagIndex);
						String tags = tagsList.toString();
						tags = tags.substring(1, tags.length() - 1).replace(", ", ",");
						song.setTags(tags);
						songListForUpdate.add(song);
						req.setAttribute("success", true);
						req.setAttribute("msg", "Tag [" + deleteTag + "] successfully deleted.");
					} else {
						req.setAttribute("success", false);
						req.setAttribute("msg", "Tag [" + deleteTag + "] couldn't be deleted.");
					}
				}
			}

			songService.updateSongTagsList(songListForUpdate);

		} else if (req.getParameter("oldTagName") != null && req.getParameter("newTagName") != null) {

			String oldTagName = req.getParameter("oldTagName");
			String newTagName = req.getParameter("newTagName");

			List<String> uniqueTagList = songService.getUniqueTagList();

			if (!uniqueTagList.contains(newTagName)) {

				List<Song> songList = songService.getSongList(false);
				List<Song> songListForUpdate = new ArrayList<Song>();

				for (Song song : songList) {
					if (song.getTags() != null && song.getTags().contains(oldTagName)) {

						List<String> tagsList = new LinkedList<String>(Arrays.asList(song.getTags().split(",")));
						int tagIndex = tagsList.indexOf(oldTagName);

						if (tagIndex > -1) {
							tagsList.set(tagIndex, newTagName);
							String tags = tagsList.toString();
							tags = tags.substring(1, tags.length() - 1).replace(", ", ",");
							song.setTags(tags);
							songListForUpdate.add(song);
							req.setAttribute("success", true);
							req.setAttribute("msg", "Tag [" + oldTagName + "] successfully updated.");
						} else {
							req.setAttribute("success", false);
							req.setAttribute("msg", "Tag [" + oldTagName + "] couldn't be updated.");
						}
					}
				}

				songService.updateSongTagsList(songListForUpdate);
			} else {
				req.setAttribute("success", false);
				req.setAttribute("msg", "Tag [" + oldTagName + "] couldn't be updated.");
			}

		}

		req.setAttribute("tags", songService.getUniqueTagList());
		req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/tags.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
