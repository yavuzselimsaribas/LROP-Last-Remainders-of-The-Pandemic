package com.cs102.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.ScreenType;

public class SettingsScreenUI extends GameRenderer {

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Slider volumeMusicSlider;
    private Slider soundMusicSlider;
    private CheckBox musicCheckbox;
    private CheckBox soundEffectsCheckbox;
    private TextButton backButton;
    private TextButton turnBackToGameButton;



    public SettingsScreenUI(final LastRemaindersOfThePandemic game) {
        super(game.getSkin());

        this.setFillParent(true);

        //volume
        volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false,game.getSkin());
        volumeMusicSlider.setValue( game.getPreferences().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });
        soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, game.getSkin());
        soundMusicSlider.setValue( game.getPreferences().getSoundVolume() );
        soundMusicSlider.addListener( new EventListener() {
                                          @Override
                                          public boolean handle(Event event) {
                                              game.getPreferences().setSoundVolume( soundMusicSlider.getValue() );
                                              return false;
                                          }
                                      }
        );
        //music
        musicCheckbox = new CheckBox(null, game.getSkin());
        musicCheckbox.setChecked( game.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });
        soundEffectsCheckbox = new CheckBox(null, game.getSkin());
        soundEffectsCheckbox.setChecked( game.getPreferences().isSoundEffectsEnabled() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled( enabled );
                return false;
            }
        });

// return to main screen button
        backButton = new TextButton("Back", game.getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(ScreenType.MENU);
            }
        });
        turnBackToGameButton = new TextButton("Back to game", game.getSkin());

        turnBackToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(ScreenType.GAME);
            }
        });

        Gdx.input.setInputProcessor(game.getStage());

        titleLabel = new Label( "Preferences", game.getSkin() );
        volumeMusicLabel = new Label( "Music Volume", game.getSkin() );
        volumeSoundLabel = new Label( "Sound Volume", game.getSkin() );
        musicOnOffLabel = new Label( "Music", game.getSkin() );
        soundOnOffLabel = new Label( "Sound", game.getSkin() );

        add(titleLabel).width(200).height(50).pad(10);
        row();
        add(volumeMusicLabel).width(200).height(50).pad(10);
        add(volumeMusicSlider).width(200).height(50).pad(10);
        row();
        add(musicOnOffLabel).width(200).height(50).pad(10);
        add(musicCheckbox).width(200).height(50).pad(10);
        row();
        add(volumeSoundLabel).width(200).height(50).pad(10);
        add(soundMusicSlider).width(200).height(50).pad(10);
        row();
        add(soundOnOffLabel).width(200).height(50).pad(10);
        add(soundEffectsCheckbox).width(200).height(50).pad(10);
        row();
        add(backButton).width(200).height(50).pad(10);
        add(turnBackToGameButton).width(200).height(50).pad(10);


    }

}
