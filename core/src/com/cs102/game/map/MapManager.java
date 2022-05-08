package com.cs102.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cs102.game.LastRemaindersOfThePandemic;

import java.util.EnumMap;
import static com.cs102.game.LastRemaindersOfThePandemic.BIT_GROUND;

public class MapManager {
    public static final String TAG = MapManager.class.getSimpleName();
    private final World world;
    private final Array<Body> bodies;
    private final AssetManager assetManager;
    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache;
    private final Array<MapListener> listeners;
    private TiledMap tiledMap;

    public MapManager(final LastRemaindersOfThePandemic mainGame) {
        currentMapType = null;
        currentMap = null;
        world = mainGame.getWorld();
        assetManager = mainGame.getAssetManager();
        bodies = new Array<Body>();
        mapCache = new EnumMap<MapType, Map>(MapType.class);
        listeners = new Array<MapListener>();
    }

    public void addMapListener(final MapListener listener) {
        listeners.add(listener);
    }

    public void setMap(final MapType type) {
        if (currentMapType == type) {
            return;
        }

        if (currentMap != null) {
            world.getBodies(bodies);
            destroyCollisionAreas();
        }

        //new map
        Gdx.app.debug(TAG, "Changing to " + type);
        currentMap = mapCache.get(type);
        currentMapType = type;
        if (currentMap == null) {
            Gdx.app.debug(TAG, "Creating the type " + type);
            assetManager.load(type.getFilePath(), TiledMap.class);
            tiledMap = assetManager.get(type.getFilePath(), TiledMap.class);
            currentMap = new Map(tiledMap);
            mapCache.put(type, currentMap);
        }

        spawnCollisionAreas();

        for (final MapListener listener : listeners) {
            listener.mapChange(currentMap);
        }
    }

    private void destroyCollisionAreas() {
        for (final Body body: bodies) {
            if (body.getUserData().equals("GROUND")) {
                world.destroyBody(body);
            }
        }
    }


    private void spawnCollisionAreas() {
        LastRemaindersOfThePandemic.resetBodiesAndFixtureDefinition();

        for (final CollisionArea collisionArea : currentMap.getCollisionAreas()) {

            LastRemaindersOfThePandemic.BODY_DEF.position.set(collisionArea.getX(), collisionArea.getY());
            LastRemaindersOfThePandemic.BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(LastRemaindersOfThePandemic.BODY_DEF);
            body.setUserData("GROUND");

            LastRemaindersOfThePandemic.FIXTURE_DEF.filter.categoryBits = BIT_GROUND;
            LastRemaindersOfThePandemic.FIXTURE_DEF.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            LastRemaindersOfThePandemic.FIXTURE_DEF.shape = chainShape;
            body.createFixture(LastRemaindersOfThePandemic.FIXTURE_DEF);
            chainShape.dispose();
        }
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
