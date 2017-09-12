package com.troy.diploclient.screen;

import static com.troy.diploclient.DiploClient.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.troy.diplo.packet.*;
import com.troy.diploclient.*;
import com.troy.diploclient.net.DiploClientNet;

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;

public class RegisterScreen implements Screen {

	private Stage stage;
	private TextField usernameField, passwordField, emailField;
	private TextButton createBtn, backBtn;
	private Label label;

	public RegisterScreen(final DiploClient game, final String username, final Settings settings) {
		this.stage = new Stage();

		this.usernameField = new TextField(username, settings.skin);
		usernameField.setMessageText("Username");

		this.passwordField = new TextField("", settings.skin);
		passwordField.setMessageText("Password");
		passwordField.setPasswordMode(true);
		passwordField.setPasswordCharacter('*');

		this.emailField = new TextField("", settings.skin);
		emailField.setMessageText("Email");

		this.createBtn = new TextButton("Create!", settings.skin);

		this.backBtn = new TextButton("Back", settings.skin);

		this.label = new Label("Create New Account", settings.skin);
		label.setAlignment(Align.center);

		setSizes(settings);

		Table container = new Table();
		container.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.add(label).prefSize(label.getWidth(), label.getHeight()).row();
		container.add(usernameField).prefSize(usernameField.getWidth(), usernameField.getHeight()).row();
		container.add(passwordField).prefSize(passwordField.getWidth(), passwordField.getHeight()).row();
		container.add(emailField).prefSize(emailField.getWidth(), emailField.getHeight()).row();
		container.add(createBtn).prefSize(createBtn.getWidth(), createBtn.getHeight()).row();
		container.add(backBtn).prefSize(backBtn.getWidth(), backBtn.getHeight()).row();
		stage.addActor(container);

		Gdx.input.setInputProcessor(stage);

		createBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String message = checkFields();
				if (message.isEmpty()) {// No issue, we can create the account
					DiploClientNet net = game.getNet();
					if (net.isConnected()) {
						net.getContext().writeAndFlush(new RegisterData(usernameField.getText().toCharArray(), passwordField.getText().toCharArray(), emailField.getText().toCharArray()));
						System.out.println("sent register to server " + usernameField.getText() + ", " + passwordField.getText());
					} else {
						GDXButtonDialog warningDialog = game.getDialogs().newDialog(GDXButtonDialog.class);
						warningDialog.setTitle("No connection!").setMessage("Check your internet connection or try again later!");
						warningDialog.addButton("Ok");
						warningDialog.setClickListener((button) -> warningDialog.dismiss());
						warningDialog.build().show();
					}
				} else {
					final GDXButtonDialog warningDialog = game.getDialogs().newDialog(GDXButtonDialog.class);
					warningDialog.setTitle("Please enter valid information!").setMessage(message);
					warningDialog.setClickListener((b) -> warningDialog.dismiss());
					warningDialog.addButton("Ok").build().show();
				}
			}
		});

		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LoginScreen(game, settings));
			}
		});
	}

	private void setSizes(Settings settings) {
		createBtn.setSize(getWidth(0.22 * settings.guiScale), getHeightAbsloute(0.1 * settings.guiScale));
		backBtn.setSize(getWidth(0.13 * settings.guiScale), getHeightAbsloute(0.075 * settings.guiScale));
		label.setSize(getWidth(0.4 * settings.guiScale), getHeightAbsloute(0.08 * settings.guiScale));
		usernameField.setSize(getWidth(0.4 * settings.guiScale), getHeightAbsloute(0.08 * settings.guiScale));
		passwordField.setSize(getWidth(0.4 * settings.guiScale), getHeightAbsloute(0.08 * settings.guiScale));
		emailField.setSize(getWidth(0.4 * settings.guiScale), getHeightAbsloute(0.08 * settings.guiScale));
	}

	private String checkFields() {
		String message = "";
		if (usernameField.getText() == null || usernameField.getText().isEmpty())
			message += "Invalid Username ";
		if (passwordField.getText() == null || passwordField.getText().isEmpty())
			message += "Invalid Password ";
		if (emailField.getText() == null || emailField.getText().isEmpty() || !emailField.getText().contains("@"))
			message += "Invalid Email ";

		return message;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
