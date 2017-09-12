package com.troy.diploclient;

import com.troy.diploclient.platformspecific.NotificationHandler;

import android.app.*;

public class AndroidNotificationManager implements NotificationHandler {

	private Activity activity;
	private int notificationId = 1;

	public AndroidNotificationManager(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void showNotification(String title, String text) {
		Notification.Builder mBuilder = new Notification.Builder(activity).setSmallIcon(R.drawable.ic_launcher).setContentTitle(title)
				.setContentText(text);

		// Gets an instance of the NotificationManager service
		NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Activity.NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		notificationManager.notify(notificationId++, mBuilder.build());
	}

}
