package com.cs102.game.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Map {
    public static final String TAG = Map.class.getSimpleName();
    private final TiledMap tiledMap;
    private final Array<CollisionArea> collisionAreas;
    public Map(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        collisionAreas = new Array<CollisionArea>();
        parseCollisionLayer();
    }

    private void parseCollisionLayer() {
        final MapLayer collisionLayer =  tiledMap.getLayers().get("collision");
        if(collisionLayer == null) {
            Gdx.app.debug(TAG, "There is no collision layer!");
            return;
        }

        final MapObjects mapObjects = collisionLayer.getObjects();
        if(mapObjects == null) {
            Gdx.app.debug(TAG, "There is no collision mapObjects defined!");
            return;
        }

        for(final MapObject mapObject : mapObjects) {
            if(mapObject instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                final float[] recVertices = new float[10];
                //left bottom
                recVertices[0] = 0;
                recVertices[1] = 0;
                //left top
                recVertices[2] = 0;
                recVertices[3] = rectangle.height;
                //right top
                recVertices[4] = rectangle.width;
                recVertices[5] = rectangle.height;
                //right bottom
                recVertices[6] = rectangle.width;
                recVertices[7] = 0;
                //left bottom
                recVertices[8] = 0;
                recVertices[9] = 0;

                collisionAreas.add(new CollisionArea(rectangle.x, rectangle.y, recVertices));
            } else if (mapObject instanceof PolylineMapObject){
                final PolylineMapObject polylineMapObject = (PolylineMapObject) mapObject;
                final Polyline polyline = polylineMapObject.getPolyline();
                CollisionArea collisionArea = new CollisionArea(polyline.getX(), polyline.getY(), polylineMapObject.getPolyline().getVertices());
                collisionAreas.add(collisionArea);
            } else {
                Gdx.app.debug(TAG, "MapObject of type " + mapObject + " is not supoorted for collision layer!");
            }
        }
    }

    public Array<CollisionArea> getCollisionAreas() {
        return collisionAreas;
    }
}
