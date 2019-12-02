package demo0;

import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import location.MyPositionServiceImpl;

public class Application0 extends Application {

    private PositionService positionService = new MyPositionServiceImpl();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();

        Position position = positionService.getPosition();
        MapPoint mapPoint = new MapPoint(position.getLatitude(), position.getLongitude());
        PoiLayer poiLayer = new PoiLayer();
        poiLayer.addPoint(mapPoint, new Circle(8, Color.RED));
        mapView.addLayer(poiLayer);
        mapView.setZoom(3);

        mapView.flyTo(1., mapPoint, 2.);

        Scene scene = new Scene(mapView);
        stage.setTitle("Gluon Maps demo");
        stage.setScene(scene);
        stage.show();
    }
}
