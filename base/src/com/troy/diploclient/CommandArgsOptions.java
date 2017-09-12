package com.troy.diploclient;

import org.apache.commons.cli.Options;

public class CommandArgsOptions extends Options {

	protected static final String DEBUG = "debug";

	public CommandArgsOptions() {
		super();
		super.addOption(DEBUG.substring(0, 1), DEBUG, false, "A boolean flag indicating weather or not debug mode is enabled");
	}

}
