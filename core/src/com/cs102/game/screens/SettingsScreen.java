package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cs102.game.LastRemaindersOfThePandemic;

public class SettingsScreen extends AbstractScreen {
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
    private Slider volumeMusicSlider;
    private Slider soundMusicSlider;
    private CheckBox musicCheckbox;
    private CheckBox soundEffectsCheckbox;
    private TextButton backButton;

    public SettingsScreen(LastRemaindersOfThePandemic game) {
        super(game);
    }


    @Override
    public void show() {
        //volume
        volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( mainGame.getPreferences().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                mainGame.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });
        soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundMusicSlider.setValue( mainGame.getPreferences().getSoundVolume() );
        soundMusicSlider.addListener( new EventListener() {
                                          @Override
                                          public boolean handle(Event event) {
                                              mainGame.getPreferences().setSoundVolume( soundMusicSlider.getValue() );
                                              return false;
                                          }
                                      }
        );
        //music
        musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( mainGame.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                mainGame.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });
        soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( mainGame.getPreferences().isSoundEffectsEnabled() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                mainGame.getPreferences().setSoundEffectsEnabled( enabled );
                return false;
            }
        });

// return to main screen button
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(ScreenType.MENU);
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        titleLabel = new Label( "Preferences", skin );
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin );
        musicOnOffLabel = new Label( "Music", skin );
        soundOnOffLabel = new Label( "Sound", skin );

        table.add(titleLabel).width(200).height(50).pad(10);
        table.row();
        table.add(volumeMusicLabel).width(200).height(50).pad(10);
        table.add(volumeMusicSlider).width(200).height(50).pad(10);
        table.row();
        table.add(musicOnOffLabel).width(200).height(50).pad(10);
        table.add(musicCheckbox).width(200).height(50).pad(10);
        table.row();
        table.add(volumeSoundLabel).width(200).height(50).pad(10);
        table.add(soundMusicSlider).width(200).height(50).pad(10);
        table.row();
        table.add(soundOnOffLabel).width(200).height(50).pad(10);
        table.add(soundEffectsCheckbox).width(200).height(50).pad(10);
        table.row();
        table.add(backButton).width(200).height(50).pad(10);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        skin.dispose();
    }

}
