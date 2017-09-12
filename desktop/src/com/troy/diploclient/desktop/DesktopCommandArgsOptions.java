package com.troy.diploclient.desktop;

import org.apache.commons.cli.Options;

public class DesktopCommandArgsOptions extends Options {

	protected static final String WIDTH = "width", HEIGHT = "height", FULLSCREEN = "fullscreen";

	public DesktopCommandArgsOptions() {
		super();
		super.addOption(WIDTH.substring(0, 1), WIDTH, true, "The width of the game window");
		super.addOption(HEIGHT.substring(0, 1), HEIGHT, true, "The height of the game window");
		super.addOption(FULLSCREEN.substring(0, 1), FULLSCREEN, false, "A flag that determenes the width of the game window");
	}

}
