package com.mahout.hsqldbtest.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.service.SongService;

@Singleton
@WebServlet(value = "/playSong", loadOnStartup = 1)
public class PlaySongServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getParameter("path");

		ServletOutputStream stream = null;
		BufferedInputStream buf = null;

		try {
			stream = resp.getOutputStream();
			File mp3 = new File(path);

			if (mp3.exists()) {

				resp.setContentType("audio/mpeg");
				resp.addHeader("Content-Disposition", "attachment; filename=" + mp3.getName());
				resp.setContentLength((int) mp3.length());

				FileInputStream input = new FileInputStream(mp3);
				buf = new BufferedInputStream(input);

				// read from the file; write to the ServletOutputStream
				int readBytes = 0;
				while ((readBytes = buf.read()) != -1) {
					stream.write(readBytes);
				}

			} else {
				throw new FileNotFoundException("File[" + path + "] not exist!");

			}

		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if (stream != null)
				stream.close();
			if (buf != null)
				buf.close();
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}