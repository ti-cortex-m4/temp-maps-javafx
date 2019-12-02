package demo2.view;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class UIPresenter {

    private static final Logger logger = Logger.getLogger(UIPresenter.class.getName());

    @FXML
    private BorderPane pane;

    @FXML
    private Label statusLabel;
    @FXML
    private MapView mapView;
    @FXML
    private ListView<String> listView;

    @FXML
    private ToggleButton showLog;

    @FXML
    private ResourceBundle resources;

    private MapPoint mapPoint;

    public void initialize() {
        logger.info("Platform: " + System.getProperty("embedded"));

        mapView = new MapView();
        mapPoint = new MapPoint(50.0d, 4.0d);
        mapView.setCenter(mapPoint);
        mapView.setZoom(15);

        PoiLayer poiLayer = new PoiLayer();
        poiLayer.addPoint(mapPoint, new Circle(7, Color.RED));
        mapView.addLayer(poiLayer);
        pane.setCenter(mapView);

        listView.managedProperty().bind(listView.visibleProperty());
        listView.visibleProperty().bind(showLog.selectedProperty());

        showLog.setSelected(false);
    }

    public void stop() {
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }

    @FXML
    private void onZoomIn() {
        if (mapView.getZoom() < 19) {
            mapView.setZoom(mapView.getZoom() + 1);
        }
    }

    @FXML
    private void onZoomOut() {
        if (mapView.getZoom() > 1) {
            mapView.setZoom(mapView.getZoom() - 1);
        }
    }
}
