package com.cs102.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import static com.cs102.game.LastRemaindersOfThePandemic.UNIT_SCALE;

public class Map {
    public static  final  String TAG = Map.class.getSimpleName();
    private final TiledMap tiledMap;
    private final Array<CollisionArea> collisionAreas;
    private final Vector2 playerStartLocation;
    private final Array<GameObject> gameObjects;
    private final IntMap<Animation<Sprite>> mapAnimations;

    Preferences pref = Gdx.app.getPreferences("b2dtut");

    public Map ( final TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        collisionAreas = new Array<>();

        parseCollisionLayer();
        playerStartLocation = parsePlayerStartLocation();
        gameObjects = new Array<GameObject>();
        mapAnimations = new IntMap<Animation<Sprite>>();
        parseGameObjectLayer();
    }

    private void parseGameObjectLayer() {
        final MapLayer gameObjectLayer = tiledMap.getLayers().get("gameObjects");
        if (gameObjectLayer == null) {
            Gdx.app.debug(TAG, "There is no gameObject layer");
            return;
        }

        final MapObjects objects = gameObjectLayer.getObjects();
        for (final MapObject mapObj : objects) {
            if (!(mapObj instanceof TiledMapTileMapObject)) {
                Gdx.app.log(TAG, "gameObject of type " + mapObj + " is not supported");
                continue;
            }

            final TiledMapTileMapObject tiledMapObj = (TiledMapTileMapObject) mapObj;
            final MapProperties tiledMapObjProperties = tiledMapObj.getProperties();
            final MapProperties tileProperties = tiledMapObj.getTile().getProperties();
            final GameObjectType gameObjType;
            if (tiledMapObjProperties.containsKey("type")) {
                gameObjType = GameObjectType.valueOf(tiledMapObjProperties.get("type", String.class));
            }
            else if(tileProperties.containsKey("type")) {
                gameObjType = GameObjectType.valueOf(tileProperties.get("type", String.class));
            }
            else {
                Gdx.app.log(TAG, "There is no object defined");
                continue;
            }

            final int animationIndex = tiledMapObj.getTile().getId();
            if (!createAnimation(animationIndex, tiledMapObj.getTile())) {
                Gdx.app.debug(TAG, "Could not create animation");
            }
            final float width = tiledMapObjProperties.get("width", Float.class) * UNIT_SCALE;
            final float height = tiledMapObjProperties.get("height", Float.class) * UNIT_SCALE;
            gameObjects.add( new GameObject(gameObjType, new Vector2(tiledMapObj.getX() * UNIT_SCALE, tiledMapObj.getY() * UNIT_SCALE), width, height, tiledMapObj.getRotation(), animationIndex));
        }
    }

    private boolean createAnimation(int animationIndex, TiledMapTile tile) {
        Animation<Sprite> animation = mapAnimations.get(animationIndex);
        if (animation == null) {
            Gdx.app.debug(TAG, "Creating new mpa animation for tile " + tile.getId());
            if (tile instanceof AnimatedTiledMapTile) {
                final AnimatedTiledMapTile aniTile = (AnimatedTiledMapTile) tile;
                final Sprite[] keyFrames = new Sprite[aniTile.getFrameTiles().length];

                int i = 0;
                for (final StaticTiledMapTile staticTile : aniTile.getFrameTiles()) {
                    keyFrames[i++] = new Sprite(staticTile.getTextureRegion());
                }
                animation = new Animation<Sprite>(aniTile.getAnimationIntervals()[0] * 0.001f, keyFrames);
                animation.setPlayMode(Animation.PlayMode.LOOP);
                mapAnimations.put(animationIndex, animation);
            }
            else if(tile instanceof StaticTiledMapTile) {
                animation = new Animation<Sprite>(0, new Sprite(tile.getTextureRegion()));
                mapAnimations.put(animationIndex, animation);
            }
            else {
                Gdx.app.debug(TAG, "tile is not supported for animation");
                return false;
            }
        }
        return true;
    }

    /*
    private Vector2 parsePlayerStartLocation() {
        float x = 0;
        float y = 0;
        if (!pref.contains("x") || !pref.contains("y")) {
            final MapLayer startLocationLayer = tiledMap.getLayers().get("playerStartLocation");
            final MapObjects objects =  startLocationLayer.getObjects();
            for (final MapObject mapObj : objects) {
                if (mapObj instanceof RectangleMapObject) {
                    final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
                    x = rectangleMapObject.getRectangle().x * UNIT_SCALE;
                    y =  rectangleMapObject.getRectangle().y * UNIT_SCALE;
                }
            }
        }
        else {
            x = pref.getFloat("x");
            y = pref.getFloat("y");
        }

        return new Vector2(x, y);
    }
    */
    private Vector2 parsePlayerStartLocation() {
        float x = 0;
        float y = 0;
        final MapLayer startLocationLayer = tiledMap.getLayers().get("playerStartLocation");
        final MapObjects objects =  startLocationLayer.getObjects();
        for (final MapObject mapObj : objects) {
            if (mapObj instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
                x = rectangleMapObject.getRectangle().x * UNIT_SCALE;
                y =  rectangleMapObject.getRectangle().y * UNIT_SCALE;
            }
        }
        return new Vector2(x, y);
    }

    public Array<CollisionArea> getCollisionAreas() {
        return collisionAreas;
    }

    //Deniz added
    public Vector2 getPlayerStartLocation() {
        return playerStartLocation;
    }
    private void parseCollisionLayer() {
        MapLayer collisionLayer = tiledMap.getLayers().get("collision");
        if (collisionLayer == null) {
            Gdx.app.debug(TAG, "There is no collision layer!");
            return;
        }

        final MapObjects mapObjects = collisionLayer.getObjects();
        if (mapObjects == null) {
            Gdx.app.debug(TAG, "There is no map objects defined!");
            return;
        }

        for (final MapObject mapObj : mapObjects) {
            if (mapObj instanceof  RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                final float[] rectVertices = new float[10];

                rectVertices[0] = 0;
                rectVertices[1] = 0;

                rectVertices[2] = 0;
                rectVertices[3] = rectangle.height;

                rectVertices[4] = rectangle.width;
                rectVertices[5] = rectangle.height;

                rectVertices[6] = rectangle.width;
                rectVertices[7] = 0;

                rectVertices[8] = 0;
                rectVertices[9] = 0;

                collisionAreas.add( new CollisionArea(rectangle.x, rectangle.y, rectVertices));
            }
            else if (mapObj instanceof PolylineMapObject) {
                final PolylineMapObject polylineMapObject = (PolylineMapObject) mapObj;
                final Polyline polyline = polylineMapObject.getPolyline();
                collisionAreas.add( new CollisionArea(polyline.getX(), polyline.getY(),polyline.getVertices()));
            }
            else {
                Gdx.app.debug(TAG, "MapObject of type " + mapObj + " is not supported");
            }
        }
    }
    public TiledMap getTiledMap() {
        return this.tiledMap;
    }

    public Array<GameObject> getGameObjects() {
        return this.gameObjects;
    }
    public IntMap<Animation<Sprite>> getMapAnimations() {
        return mapAnimations;
    }
}
