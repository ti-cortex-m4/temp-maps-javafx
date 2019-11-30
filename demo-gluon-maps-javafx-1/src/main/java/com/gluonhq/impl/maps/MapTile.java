package com.gluonhq.impl.maps;

import com.gluonhq.maps.tile.TileRetrieverProvider;
import com.gluonhq.maps.tile.TileRetriever;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Math.floor;

/**
 *
 */
class MapTile extends Region {

    private static final Logger logger = Logger.getLogger( MapTile.class.getName() );
    private static final TileRetriever TILE_RETRIEVER = TileRetrieverProvider.getInstance().load();

    final int myZoom;
    final long i, j;
    private final BaseMap baseMap;
    // a list of tiles that this tile is covering. In case the covered tiles are 
    // not yet loaded, this tile will be rendered.
    private final List<MapTile> coveredTiles = new LinkedList<>();
    /**
     * In most cases, a tile will be shown scaled. The value for the scale
     * factor depends on the active zoom and the tile-specific myZoom
     */
    private final Scale scale = new Scale();

    public boolean isCovering() {
        return coveredTiles.size() > 0;
    }

    private final InvalidationListener zl = o -> calculatePosition();
    private ReadOnlyDoubleProperty progress;

    MapTile(BaseMap baseMap, int nearestZoom, long i, long j) {
        this.baseMap = baseMap;
        this.myZoom = nearestZoom;
        this.i = i;
        this.j = j;
        scale.setPivotX(0);
        scale.setPivotY(0);
        getTransforms().add(scale);
        debug("[JVDBG] load image [" + myZoom + "], i = " + i + ", j = " + j);

        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);
        Image tile = TILE_RETRIEVER.loadTile(myZoom, i, j);
        imageView.setImage(tile);
        this.progress = tile.progressProperty();

//        Label l = new Label("Tile [" + myZoom + "], i = " + i + ", j = " + j);
        getChildren().addAll(imageView);//,l);
        this.progress.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (progress.get() >= 1.0d) {
                    debug("[JVDBG] got image [" + myZoom + "], i = " + i + ", j = " + j);
                    setNeedsLayout(true);
                    progress.removeListener(this);
                }
            }
        });
        baseMap.zoom().addListener(new WeakInvalidationListener(zl));
        baseMap.translateXProperty().addListener(new WeakInvalidationListener(zl));
        baseMap.translateYProperty().addListener(new WeakInvalidationListener(zl));
        calculatePosition();
        this.setMouseTransparent(true);
    }

    boolean loading() {
        return !(progress.greaterThanOrEqualTo(1.)).get();
    }

    /**
     * The immutable zoomlevel for this tile.
     *
     * @return
     */
    int getZoomLevel() {
        return myZoom;
    }

    private void calculatePosition() {
        double currentZoom = baseMap.zoom().get();
        int visibleWindow = (int) floor(currentZoom + BaseMap.TIPPING);
        boolean visible =  visibleWindow == myZoom ||
                           isCovering() ||
                           ((visibleWindow >= BaseMap.MAX_ZOOM) && (myZoom == BaseMap.MAX_ZOOM - 1));
        this.setVisible(visible);
        logger.fine("visible tile " + this + "? " + this.isVisible() + (this.isVisible() ? " covering? " + isCovering() : ""));
        double sf = Math.pow(2, currentZoom - myZoom);
        scale.setX(sf);
        scale.setY(sf);
        setTranslateX(256 * i * sf);
        setTranslateY(256 * j * sf);
    }

    @Override
    public String toString() {
        return "tile with z = " + myZoom + " [" + i + "," + j + "]";
    }

    /**
     * This tile is covering for the child tile that is still being loaded.
     *
     * @param child
     */
    void addCovering(MapTile child) {
        coveredTiles.add(child);
        InvalidationListener il = createProgressListener(child);
//        System.out.println("We have to cover, add "+il);
        child.progress.addListener(il);
        calculatePosition();
    }

    private InvalidationListener createProgressListener(MapTile child) {
        return new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if (child.progress.get() >= 1.0d) {
                    MapTile.this.coveredTiles.remove(child);
                    child.progress.removeListener(this);
                }
            }
        };
    }

    private void debug(String s) {
        logger.fine("LOG " + System.currentTimeMillis() % 10000 + ": " + s);
    }
}
