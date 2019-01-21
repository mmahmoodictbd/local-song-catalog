package com.mahout.hsqldbtest.listener;

import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.mahout.hsqldbtest.SongModule;

@WebListener
public class BootstrapContextListener extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new SongModule());
	}
}