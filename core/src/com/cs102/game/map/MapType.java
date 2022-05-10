package com.cs102.game.map;

public enum MapType {
    MAP_1( "deneme/adsız.tmx"),
    MAP_2("map/map.tmx");

    private final String filePath;

    MapType( final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
