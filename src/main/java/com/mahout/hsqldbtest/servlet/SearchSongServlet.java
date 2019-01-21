package com.mahout.hsqldbtest.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.bean.SearchParameters;
import com.mahout.hsqldbtest.bean.SearchResultBean;
import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.SongService;
import com.mahout.hsqldbtest.util.Utils;

@Singleton
@WebServlet(value = "/searchSong", loadOnStartup = 1)
public class SearchSongServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String searchString = req.getParameter("searchString");

		SearchResultBean searchResultBean = new SearchResultBean();
		searchResultBean.setSearchString(searchString != null ? searchString.trim() : "");
		searchResultBean.setTagList(Utils.listToJSON(songService.getUniqueTagList()));
		searchResultBean.setPlaylistsTagList(Utils.listToJSON(songService.getUniquePlaylistTagList()));

		searchResultBean.setSearchOnlyUntagged(req.getParameter("searchOnlyUntagged") != null ? true : false);
		searchResultBean.setSearchExcludeFileName(req.getParameter("searchExcludeFileName") != null ? true : false);
		searchResultBean.setSearchExcludeTags(req.getParameter("searchExcludeTags") != null ? true : false);
		searchResultBean.setSearchExcludePlaylists(req.getParameter("searchExcludePlaylists") != null ? true : false);
		searchResultBean.setSearchExcludeFilePath(req.getParameter("searchExcludeFilePath") != null ? true : false);
		searchResultBean.setSearchExcludeLyrics(req.getParameter("searchExcludeLyrics") != null ? true : false);

		SearchParameters searchParam = new SearchParameters();
		searchParam.setSearchString(searchResultBean.getSearchString());
		searchParam.setSearchOnlyUntagged(searchResultBean.isSearchOnlyUntagged());
		searchParam.setSearchExcludeFileName(searchResultBean.isSearchExcludeFileName());
		searchParam.setSearchExcludeTags(searchResultBean.isSearchExcludeTags());
		searchParam.setSearchExcludePlaylists(searchResultBean.isSearchExcludePlaylists());
		searchParam.setSearchExcludeFilePath(searchResultBean.isSearchExcludeFilePath());
		searchParam.setSearchExcludeLyrics(searchResultBean.isSearchExcludeLyrics());

		List<Song> songList = songService.getSongList(searchParam);

		for (Song song : songList) {
			song.setFileName(Utils.fileNamePrettifier(song.getFileName()));
			song.setCanonicalPath(song.getCanonicalPath());
		}
		searchResultBean.setSongList(songList);

		req.setAttribute("searchResultBean", searchResultBean);
		req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/searchSong.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
