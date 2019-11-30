package com.gluonhq.maps.tile;

import com.gluonhq.impl.maps.tile.osm.CachedOsmTileRetriever;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TileRetrieverProvider {

    private static TileRetrieverProvider provider;
    public static synchronized TileRetrieverProvider getInstance() {
        if (provider == null) {
            provider = new TileRetrieverProvider();
        }
        return provider;
    }

    private final ServiceLoader<TileRetriever> loader;

    private TileRetrieverProvider() {
        loader = ServiceLoader.load(TileRetriever.class);
    }

    public TileRetriever load() {
        Iterator<TileRetriever> tileRetrievers = loader.iterator();
        if (tileRetrievers.hasNext()) {
            return tileRetrievers.next();
        } else {
            return new CachedOsmTileRetriever();
        }
    }
}
