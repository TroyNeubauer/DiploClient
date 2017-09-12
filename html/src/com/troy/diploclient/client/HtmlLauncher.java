package com.troy.diploclient.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.troy.diploclient.DiploClient;
import com.troy.diploclient.platformspecific.NotificationHandler;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(1280, 720);
	}

	@Override
	public ApplicationListener createApplicationListener() {
		return new DiploClient(new String[0], new NotificationHandler() {

			@Override
			public void showNotification(String title, String text) {
				// Nothing because this is web
			}

		});
	}
}