package com.troy.diploclient;

import javax.management.NotificationListener;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.troy.diploclient.DiploClient;
import com.troy.diploclient.platformspecific.NotificationHandler;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationPortrait = false;
        config.orientationLandscape = true;
        
        config.allowIpod = true;
        config.keyboardCloseOnReturn = true;
        
        config.useAccelerometer = false;
        config.useCompass = false;
        
        NotificationHandler handler = new IOSNotificationsHandler();
        
        DiploClient client = new DiploClient(new String[0], handler);
        
        return new IOSApplication(client, config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}