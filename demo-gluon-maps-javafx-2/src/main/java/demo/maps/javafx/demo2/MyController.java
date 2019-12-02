package demo.maps.javafx.demo2;

import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import demo.maps.javafx.location.MyPositionServiceImpl;
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

    private PositionService positionService = new MyPositionServiceImpl();

    public void initialize() {
        mapView = new MapView();
        Position position = positionService.getPosition();
        MapPoint mapPoint = new MapPoint(position.getLatitude(), position.getLongitude());
        mapView.setCenter(mapPoint);
        mapView.setZoom(3);

        PoiLayer poiLayer = new PoiLayer();
        poiLayer.addPoint(mapPoint, new Circle(8, Color.RED));
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
