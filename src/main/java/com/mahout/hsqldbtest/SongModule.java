package com.mahout.hsqldbtest;

import com.google.inject.AbstractModule;
import com.mahout.hsqldbtest.service.DbService;
import com.mahout.hsqldbtest.service.SongService;

public class SongModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(SongService.class);
		bind(DbService.class);
	}

}