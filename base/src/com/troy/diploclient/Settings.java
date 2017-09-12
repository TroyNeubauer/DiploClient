package com.troy.diploclient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Settings {

	public float guiScale;

	private DiploClient game;

	public Skin skin;

	public Settings(DiploClient game) {
		this.game = game;
		this.skin = new Skin(Gdx.files.internal("default.json"));
		this.guiScale = game.isMobile() ? 1.5f : 1.0f;
	}

	public DiploClient getGame() {
		return game;
	}

}
