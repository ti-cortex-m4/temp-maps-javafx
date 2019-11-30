package demo;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Application0 extends Application {

    public static final double MYLAT = 57.66;
    public static final double MYLON = 12.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();

        MapPoint mapPoint = new MapPoint(MYLAT, MYLON);
        PoiLayer poiLayer = new PoiLayer();
        poiLayer.addPoint(mapPoint, new Circle(8, Color.RED));
        mapView.addLayer(poiLayer);
        mapView.setZoom(3);

        mapView.flyTo(1., mapPoint, 2.);

        Scene scene = new Scene(mapView);
        stage.setTitle("Embedded Maps");
        stage.setScene(scene);
        stage.show();

//        mStage = primaryStage;
//        primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("majincline-globe-1024px.png")));
//
//        createUI();
//        initAccelerators();
//        mStage.setTitle(APP_TITLE + " - a collection of map renderers for Java desktop applications");
//        mStage.show();
    }
}
