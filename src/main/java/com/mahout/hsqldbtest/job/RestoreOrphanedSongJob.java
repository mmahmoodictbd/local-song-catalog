package com.mahout.hsqldbtest.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mahout.hsqldbtest.model.Song;
import com.mahout.hsqldbtest.service.DbService;
import com.mahout.hsqldbtest.service.SongService;

public class RestoreOrphanedSongJob implements Job {

	private static SongService songService = new SongService(new DbService());

	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		if (songService.isRestoreOrphanedSongPossible()) {

			List<String> restorableSongInfoMD5StringList = songService.restorableSongInfoMD5StringList();
			for (String infoMD5String : restorableSongInfoMD5StringList) {

				Song orphanedSong = songService.getOrphanedSongByInfoMD5String(infoMD5String);

				Song song = songService.getSongByInfoMD5String(infoMD5String);
				song.setTags(orphanedSong.getTags());
				song.setPlayliststags(orphanedSong.getPlayliststags());
				song.setLyrics(orphanedSong.getLyrics());

				songService.updateSong(song);
				songService.deleteOrphanedSong(orphanedSong.getId());
			}

		}
	}
}
