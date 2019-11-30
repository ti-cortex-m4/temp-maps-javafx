package com.gluonhq.impl.maps.tile.osm;

import com.gluonhq.maps.tile.TileRetriever;
import javafx.scene.image.Image;

public class OsmTileRetriever implements TileRetriever {

    private static final String host = "http://tile.openstreetmap.org/";
    static final String httpAgent;

    static {
        String agent = System.getProperty("http.agent");
        if (agent == null) {
            agent = "(" + System.getProperty("os.name") + " / " + System.getProperty("os.version") + " / " + System.getProperty("os.arch") + ")";
        }
        httpAgent = "Gluon Maps/2.0.0 " + agent;
        System.setProperty("http.agent", httpAgent);
    }

    static String buildImageUrlString(int zoom, long i, long j) {
        return host + zoom + "/" + i + "/" + j + ".png";
    }

    @Override
    public Image loadTile(int zoom, long i, long j) {
        String urlString = buildImageUrlString(zoom, i, j);
        return new Image(urlString, true);
    }
}
