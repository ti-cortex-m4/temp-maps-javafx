package demo1;

import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.gluonhq.attach.util.Services;
import com.gluonhq.attach.util.impl.DefaultServiceFactory;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import demo.maps.javafx.location.MyPositionServiceImpl;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application1 extends Application {

    private static final Logger logger = Logger.getLogger(Application1.class.getName());

    private MapPoint mapPoint;

    @Override
    public void start(Stage stage) {
        Services.registerServiceFactory(new DefaultServiceFactory<>(PositionService.class) {
            @Override
            public Optional<PositionService> getInstance() {
                return Optional.of(new MyPositionServiceImpl());
            }
        });

        MapView view = new MapView();
        view.addLayer(getMapLayer());
        view.setZoom(3);

        Scene scene = new Scene(view);
        stage.setTitle("Gluon Maps demo");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        if (mapPoint != null) {
            view.flyTo(1., mapPoint, 2.);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("It's impossible to get current position");
            alert.setContentText("Position service is not available");
            alert.showAndWait();
        }
    }

    private MapLayer getMapLayer() {
        return getPosition()
                .map(position -> {
                    mapPoint = new MapPoint(position.getLatitude(), position.getLongitude());
                    PoiLayer answer = new PoiLayer();
                    answer.addPoint(mapPoint, new Circle(8, Color.RED));
                    return answer;
                })
                .orElseGet(() -> {
                    logger.log(Level.WARNING, "Position service is not available");
                    return new PoiLayer();
                });
    }

    private Optional<Position> getPosition() {
        return Services.get(PositionService.class)
                .map(positionService -> {
                    positionService.start();
                    ReadOnlyObjectProperty<Position> positionProperty = positionService.positionProperty();
                    return Optional.ofNullable(positionProperty.get());
                })
                .orElseGet(Optional::empty);
    }
}