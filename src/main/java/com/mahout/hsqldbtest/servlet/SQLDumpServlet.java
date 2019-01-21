package com.mahout.hsqldbtest.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hsqldb.lib.tar.DbBackup;
import org.hsqldb.lib.tar.TarMalformatException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mahout.hsqldbtest.service.SongService;

@Singleton
@WebServlet(value = "/sqldump", loadOnStartup = 1)
public class SQLDumpServlet extends AbstractInjectableServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SongService songService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String tmpDir = System.getProperty("java.io.tmpdir");
		DbBackup dbBackup = new DbBackup(new File(tmpDir + "/db.tar"), "db/myDb");

		try {
			dbBackup.setOverWrite(true);
			dbBackup.setAbortUponModify(false);
			dbBackup.write();
		} catch (TarMalformatException e) {
			e.printStackTrace();
		}

		File backupTarDb = new File(tmpDir + "/db.tar");
		if (backupTarDb.exists()) {
			ServletOutputStream sos = resp.getOutputStream();
			resp.setContentType("application/x-tar");
			resp.setHeader("Content-Disposition", "attachment; filename=\"db.tar\"");

			sos.write(IOUtils.toByteArray(new FileInputStream(backupTarDb)));
			sos.flush();

			backupTarDb.deleteOnExit();
		} else {
			throw new FileNotFoundException("DEBUG:: Backup tar not generated!");
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}