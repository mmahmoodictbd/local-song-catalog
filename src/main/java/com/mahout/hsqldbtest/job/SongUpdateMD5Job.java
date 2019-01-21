package com.mahout.hsqldbtest.job;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.DbService;
import com.mahout.hsqldbtest.service.SongService;
import com.mahout.hsqldbtest.util.Utils;

public class SongUpdateMD5Job implements Job {

	private static SongService songService = new SongService(new DbService());
	private static List<Song> songForMD5UpdateList = new ArrayList<Song>();
	private static int timeToComplete = 30;
	private static int updateRate = 0;

	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		if (songForMD5UpdateList.isEmpty()) {

			if (songService.isSongExistForMD5Update()) {

				songForMD5UpdateList = songService.getSongListForMD5Update();
				updateRate = (int) Math.ceil((double) songForMD5UpdateList.size() / timeToComplete);

			}
		} else {

			List<Song> updatedMd5SongList = new ArrayList<Song>();
			for (int i = 0; i < updateRate; i++) {

				Song song = songForMD5UpdateList.get(0);
				File file = new File(song.getCanonicalPath());

				if (file.exists()) {
					song.setInfoMD5String(Utils.getMD5String(file));
					updatedMd5SongList.add(song);
				}
				songForMD5UpdateList.remove(0);
			}

			if (!updatedMd5SongList.isEmpty()) {
				songService.updateSongMD5List(updatedMd5SongList);
			}

		}

	}
}
