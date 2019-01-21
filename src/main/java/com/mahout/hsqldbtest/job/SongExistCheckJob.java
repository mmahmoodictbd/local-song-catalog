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

public class SongExistCheckJob implements Job {

	private static SongService songService = new SongService(new DbService());
	private static List<String> filePathListToExistanceCheck = new ArrayList<String>();
	private static int timeToComplete = 30;
	private static int updateRate = 0;

	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		if (filePathListToExistanceCheck.isEmpty()) {

			filePathListToExistanceCheck = songService.getCanonicalPathList();
			updateRate = (int) Math.ceil((double) filePathListToExistanceCheck.size() / timeToComplete);

		} else {

			List<Song> deleteSongList = new ArrayList<Song>();
			List<Song> orphanedSongList = new ArrayList<Song>();
			for (int i = 0; i < updateRate; i++) {

				String canonicalPath = filePathListToExistanceCheck.get(0);
				File file = new File(canonicalPath);

				if (!file.exists()) {

					Song song = songService.getSongByCanonicalPath(canonicalPath);
					deleteSongList.add(song);

					if (((song.getTags() != null && song.getTags().length() > 0) || (song.getPlayliststags() != null && song
							.getPlayliststags().length() > 0))
							&& (song.getInfoMD5String() != null && song.getInfoMD5String().length() > 0)) {
						orphanedSongList.add(song);
					}

				}
				filePathListToExistanceCheck.remove(0);
			}

			if (!deleteSongList.isEmpty()) {
				songService.deleteSongList(deleteSongList);
			}

			if (!orphanedSongList.isEmpty()) {

				for (Song song : orphanedSongList) {
					song.setId(null);
				}
				songService.saveOrphanedSongList(orphanedSongList);
			}

		}

	}

}
