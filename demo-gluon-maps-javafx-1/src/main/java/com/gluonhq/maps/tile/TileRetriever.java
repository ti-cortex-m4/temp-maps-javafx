package com.gluonhq.maps.tile;

import javafx.scene.image.Image;

public interface TileRetriever {

    /**
     * Loads a tile at the specified zoom level and coordinates and returns it
     * as an {@link Image}.
     *
     * @param zoom the desired zoom level for the tile to load
     * @param i the horizontal position of the tile to load
     * @param j the vertical position of the tile to load
     * @return an image representing the tile
     */
    Image loadTile(int zoom, long i, long j);
}
