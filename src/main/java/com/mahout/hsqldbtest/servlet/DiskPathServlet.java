package com.mahout.hsqldbtest.servlet;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.service.SongService;
import com.mahout.hsqldbtest.util.Utils;

@Singleton
@WebServlet(value = "/diskPathSuggestion", loadOnStartup = 1)
public class DiskPathServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String searchPath = req.getParameter("q");

		if (searchPath != null && searchPath.length() != 0) {

			searchPath = searchPath.substring(0, searchPath.lastIndexOf(File.separatorChar));

			File file = new File(searchPath);
			String[] directories = file.list(new FilenameFilter() {
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});

			if (directories != null && directories.length > 0) {
				resp.getWriter().write(Utils.listToJSON(Arrays.asList(directories)));

			} else {
				resp.getWriter().write("[]");
			}

		} else {
			resp.getWriter().write("[]");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
