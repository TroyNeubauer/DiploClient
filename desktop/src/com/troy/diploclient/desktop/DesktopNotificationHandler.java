package com.troy.diploclient.desktop;

import com.notification.*;
import com.notification.NotificationFactory.Location;
import com.notification.manager.SimpleManager;
import com.notification.types.TextNotification;
import com.theme.ThemePackagePresets;
import com.troy.diploclient.platformspecific.NotificationHandler;
import com.utils.Time;

public class DesktopNotificationHandler implements NotificationHandler {

	private NotificationManager manager = new SimpleManager(Location.SOUTHEAST);

	@Override
	public void showNotification(String title, String text) {
		// makes a factory with the built-in clean theme
		NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
		// creates a text notification
		TextNotification notification = factory.buildTextNotification(title, text);
		notification.setCloseOnClick(true);
		notification.setOpacity(0.7);
		
		manager.addNotification(notification, Time.infinite());
	}

}
