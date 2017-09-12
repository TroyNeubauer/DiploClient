package com.troy.diploclient;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL20;
import com.troy.diploclient.net.DiploClientNet;
import com.troy.diploclient.platformspecific.NotificationHandler;
import com.troy.diploclient.screen.LoginScreen;

import de.tomgrill.gdxdialogs.core.*;

public class DiploClient extends Game {

	public GDXDialogs dialogs;

	private boolean isDesktop;
	private Settings settings;
	private NotificationHandler handler;
	private DiploClientNet net;

	public DiploClient(String[] args, NotificationHandler handler) {
		this.handler = handler;
	}

	@Override
	public void create() {
		net = new DiploClientNet();
		dialogs = GDXDialogsSystem.install();
		this.isDesktop = Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.Applet
				|| Gdx.app.getType() == ApplicationType.HeadlessDesktop;
		this.settings = new Settings(this);
		setScreen(new LoginScreen(this, settings));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		net.close();
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public static float getWidth(double relative) {
		return (float) (relative * Gdx.graphics.getWidth());
	}

	public static float getHeightAbsloute(double relative) {
		return (float) (relative * Gdx.graphics.getHeight());
	}

	public static float getHeightFlipped(double relative) {
		relative = 1.0 - relative;
		return (float) (relative * Gdx.graphics.getHeight());
	}

	public boolean isDesktop() {
		return isDesktop;
	}

	public boolean isMobile() {
		return !isDesktop;
	}

	public Settings getSettings() {
		return settings;
	}

	public DiploClientNet getNet() {
		return net;
	}

	public GDXDialogs getDialogs() {
		return dialogs;
	}
	
	public NotificationHandler getNotificationHandler() {
		return handler;
	}
}
