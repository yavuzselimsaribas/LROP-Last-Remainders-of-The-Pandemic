package com.cs102.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.cs102.game.LastRemaindersOfThePandemic.UNIT_SCALE;

public class Map {
    public static  final  String TAG = Map.class.getSimpleName();
    private final TiledMap tiledMap;
    private final Array<CollisionArea> collisionAreas;
    private final Vector2 playerStartLocation;

    public Map ( final TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        collisionAreas = new Array<>();

        parseCollisionLayer();
        playerStartLocation = new Vector2();
        parsePlayerStartLocation();
    }

    private void parsePlayerStartLocation() {
        final MapLayer startLocationLayer = tiledMap.getLayers().get("playerStartLocation");
        if (startLocationLayer == null) {
            Gdx.app.debug(TAG, "There is no start location layer");
            return;
        }

        final MapObjects objects =  startLocationLayer.getObjects();
        for (final MapObject mapObj : objects) {
            if (mapObj instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
                playerStartLocation.set(rectangleMapObject.getRectangle().x * UNIT_SCALE, rectangleMapObject.getRectangle().y * UNIT_SCALE);
            }
        }
    }

    public Array<CollisionArea> getCollisionAreas() {
        return collisionAreas;
    }

    //Deniz added
    public Vector2 getPlayerStartLocation() {
        return playerStartLocation;
    }
    private void parseCollisionLayer() {
        final MapLayer collisionLayer = tiledMap.getLayers().get("collision");
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
}
