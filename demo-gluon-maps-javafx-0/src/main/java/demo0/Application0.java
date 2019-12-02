package demo0;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Application0 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();

        MapPoint mapPoint = new MapPoint(52.516667, 13.388889);
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
