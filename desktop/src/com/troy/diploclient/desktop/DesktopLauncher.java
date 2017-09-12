package com.troy.diploclient.desktop;

import org.apache.commons.cli.*;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.*;
import com.troy.diplo.game.DiploConstants;
import com.troy.diploclient.*;
import com.troyberry.util.MiscUtil;

import static com.troy.diploclient.desktop.DesktopCommandArgsOptions.*;

public class DesktopLauncher {
	public static void main(String[] args) {
		/*
		 * TurnTimeSchoolProvider pro = new TurnTimeSchoolProvider(Calendar.getInstance().get(Calendar.MONTH),
		 * Calendar.getInstance().get(Calendar.DAY_OF_MONTH), DayBlock.BURGUNDY, 4, 00); for(int i = 0; i < 100; i++) { TurnTime time =
		 * pro.next(); System.out.println(time); } System.out.println(pro.getPattern());
		 * 
		 * 
		 * System.exit(0);
		 * 
		 * TurnStrategy s = new TurnStrategy(new TurnTimeRepeatableProvider(System.currentTimeMillis(), TimeUnit.DAYS.toMillis(2)));
		 * s.refillQueue(10); for(int i = 0; i < 10; i++) { TurnTime time = s.next(); System.out.println(time); }
		 * 
		 * System.exit(0);
		 */
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		CommandLine options = null;
		try {
			options = new DefaultParser().parse(new DesktopCommandArgsOptions(), args);
		} catch (ParseException e) {
			System.err.println("Unable to parse command line args!\n" + MiscUtil.getStackTrace(e));
			return;
		}

		boolean fullscreen = options.hasOption(FULLSCREEN);
		boolean suppliedWidthAndHeight = options.hasOption(WIDTH) && options.hasOption(HEIGHT);
		DisplayMode fullscreenMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		if (fullscreen && !suppliedWidthAndHeight) {
			config.setFromDisplayMode(fullscreenMode);// sets bits values
			config.fullscreen = true;
			config.width = fullscreenMode.width;
			config.height = fullscreenMode.height;
		} else if (suppliedWidthAndHeight) {
			if (fullscreen)
				config.fullscreen = true;
			else
				config.fullscreen = false;
			//format:off
			config.width =  MiscUtil.getIntOrRunnableAndDefValue(options.getOptionValue(HEIGHT), () -> {System.err.println("Invalid width!");System.exit(1);}, -1);
			config.height = MiscUtil.getIntOrRunnableAndDefValue(options.getOptionValue(HEIGHT), () -> {System.err.println("Invalid height!");System.exit(1);}, -1);
			//format:on
			if (fullscreenMode.bitsPerPixel == 16) {
				config.r = 5;
				config.g = 6;
				config.b = 5;
				config.a = 0;
			}
			if (fullscreenMode.bitsPerPixel == 24) {
				config.r = config.g = config.b = 8;
				config.a = 0;
			}
			if (fullscreenMode.bitsPerPixel == 32) {
				config.r = config.g = config.b = config.a = 8;
			}
		}
		if (options.hasOption(WIDTH) ^ options.hasOption(HEIGHT)) {
			System.out.println("Warning, only a " + (options.hasOption(WIDTH) ? "width" : "height") + " was specified");
		}

		new LwjglApplication(new DiploClient(args, new DesktopNotificationHandler()), config);
	}
}
