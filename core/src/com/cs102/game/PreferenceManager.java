package com.cs102.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.PlayerComponent;

public class PreferenceManager implements Json.Serializable{
    private final Preferences preferences;
    private final Json json;
    private final JsonReader jsonReader;
    private Vector2 playerPos;
    private int itemCollected;
    private int health;

    public PreferenceManager() {
        preferences = Gdx.app.getPreferences("CS102_Preferences");
        json = new Json();
        jsonReader = new JsonReader();
        playerPos = new Vector2();
        health = 0;
    }

    public boolean containsKey(String key) {
        return preferences.contains(key);
    }
    public void setFloatValue(String key, float value) {
        preferences.putFloat(key, value);
        preferences.flush();
    }
    public float getFloatValue(String key) {
        return preferences.getFloat(key, 0.0f);
    }

    public void saveGameState(Entity player) {
        playerPos.set(ECSEngine.b2dCmpMapper.get(player).body.getPosition());
        itemCollected = ECSEngine.playerCmpMapper.get(player).itemCount;
        health = ECSEngine.playerCmpMapper.get(player).health;

        preferences.putString("GAME_STATE", new Json().toJson(this));

        preferences.flush();
    }
    public void loadGameState(Entity player) {
        final JsonValue savedJsonStr = jsonReader.parse(preferences.getString("GAME_STATE"));

        final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(player);
        b2DComponent.body.setTransform(savedJsonStr.getFloat("PLAYER_X", 0f),savedJsonStr.getFloat("PLAYER_Y", 0f),b2DComponent.body.getAngle());
        final PlayerComponent playerComponent = ECSEngine.playerCmpMapper.get(player);
        playerComponent.itemCount = savedJsonStr.getInt("ITEM_COLLECTED", 0);
        playerComponent.health = savedJsonStr.getInt("HEALTH", 100);
    }

    @Override
    public void write(Json json) {
        json.writeValue("PLAYER_X", playerPos.x);
        json.writeValue("PLAYER_Y", playerPos.y);
        json.writeValue("ITEM_COLLECTED", itemCollected);
        json.writeValue("HEALTH", health);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}

