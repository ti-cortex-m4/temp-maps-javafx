package demo2;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ResourceBundle;

public class MyController {

    @FXML
    private BorderPane pane;

    @FXML
    private MapView mapView;

    @FXML
    private ResourceBundle resources;

    public void initialize() {
        mapView = new MapView();
        MapPoint mapPoint = new MapPoint(51.507222, -0.1275);
        mapView.setCenter(mapPoint);
        mapView.setZoom(10);

        PoiLayer poiLayer = new PoiLayer();
        poiLayer.addPoint(mapPoint, new Circle(7, Color.RED));
        mapView.addLayer(poiLayer);
        pane.setCenter(mapView);
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

    @FXML
    private void onExit() {
        Platform.exit();
    }
}
