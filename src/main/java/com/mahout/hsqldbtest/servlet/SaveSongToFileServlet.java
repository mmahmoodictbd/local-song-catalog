package com.mahout.hsqldbtest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.bean.SearchParameters;
import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.SongService;

@Singleton
@WebServlet(value = "/savePlaylistToFile", loadOnStartup = 1)
public class SaveSongToFileServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String playlistName = req.getParameter("playlist");

		if (playlistName != null && playlistName.length() > 0) {

			SearchParameters searchParam = new SearchParameters();
			searchParam.setSearchString(playlistName);
			searchParam.setSearchOnlyUntagged(false);
			searchParam.setSearchExcludeFileName(true);
			searchParam.setSearchExcludeTags(true);
			searchParam.setSearchExcludePlaylists(false);
			searchParam.setSearchExcludeFilePath(true);
			searchParam.setSearchExcludeLyrics(true);

			List<Song> songList = songService.getSongList(searchParam);

			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("#EXTM3U\n");
			for (Song song : songList) {
				sBuffer.append("#EXTINF:," + song.getFileName() + "\n");
				sBuffer.append(song.getCanonicalPath()+"\n");
			}
			
			resp.setContentType("audio/x-mpegurl");
			resp.setContentLength(sBuffer.toString().length());
			resp.addHeader("Content-Disposition", "attachment; filename=" + playlistName + ".m3u");

			PrintWriter out = resp.getWriter();
			out.println(sBuffer.toString());
			out.close();
		}



	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}