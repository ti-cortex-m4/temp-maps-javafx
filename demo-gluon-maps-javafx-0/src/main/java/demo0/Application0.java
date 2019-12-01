package demo0;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Application0 extends Application {

    private static final double LATITUDE = 57.66;
    private static final double LONGITUDE = 12.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();

        MapPoint mapPoint = new MapPoint(LATITUDE, LONGITUDE);
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
