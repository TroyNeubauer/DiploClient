package com.troy.diploclient;

import org.robovm.apple.foundation.*;
import org.robovm.apple.uikit.*;

import com.troy.diploclient.platformspecific.NotificationHandler;

@SuppressWarnings("deprecation")
public class IOSNotificationsHandler implements NotificationHandler {
	
	public IOSNotificationsHandler() {
		
        //Removes notifications indicator in app icon, you can do this in a different way
        UIApplication.getSharedApplication().setApplicationIconBadgeNumber(0);
        UIApplication.getSharedApplication().cancelAllLocalNotifications();
	}

	@Override
	public void showNotification(String title, String text) {
		NSDate date = new NSDate();
        //1 seconds from now
        NSDate secondsMore = date.newDateByAddingTimeInterval(1);

        UILocalNotification localNotification = new UILocalNotification();
        localNotification.setFireDate(secondsMore);
        localNotification.setAlertBody(title);
        localNotification.setAlertAction(text);
        localNotification.setTimeZone(NSTimeZone.getDefaultTimeZone());
        localNotification.setApplicationIconBadgeNumber(UIApplication.getSharedApplication().getApplicationIconBadgeNumber() + 1);

        UIApplication.getSharedApplication().scheduleLocalNotification(localNotification);
    
	}

}
