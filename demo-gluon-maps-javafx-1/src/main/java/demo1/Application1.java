package demo1;

import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.attach.util.impl.DefaultServiceFactory;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import demo.maps.javafx.location.MyPositionServiceImpl;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application1 extends Application {

    private static final Logger logger = Logger.getLogger(Application1.class.getName());

//    private MapPoint mapPoint;

    @Override
    public void start(Stage stage) {
        Services.registerServiceFactory(new DefaultServiceFactory<>(PositionService.class) {
            @Override
            public Optional<PositionService> getInstance() {
                return Optional.of(new MyPositionServiceImpl());
            }
        });

        MapView view = new MapView();
        view.addLayer(positionLayer());
        view.setZoom(3);
        Scene scene;
        if (Platform.isDesktop()) {
            scene = new Scene(view, 600, 700);
            stage.setTitle("Gluon Maps demo");
        } else {
            BorderPane bp = new BorderPane();
            bp.setCenter(view);

            Label label = new Label("Gluon Maps demo");
            label.setAlignment(Pos.CENTER);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setStyle("-fx-background-color: dimgrey; -fx-text-fill: white;");

            bp.setTop(label);
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            scene = new Scene(bp, bounds.getWidth(), bounds.getHeight());
        }
        stage.setScene(scene);
        stage.show();

//        view.flyTo(1., mapPoint, 2.);
    }

    private MapLayer positionLayer() {
        return Services.get(PositionService.class)
                .map(positionService -> {
                    positionService.start();

                    ReadOnlyObjectProperty<Position> positionProperty = positionService.positionProperty();
                    Position position = positionProperty.get();
//                    if (position == null) { TODO
//                        position = new Position(48.8567, 2.3508);
//                    }
                    MapPoint mapPoint = new MapPoint(position.getLatitude(), position.getLongitude());
//                    logger.log(Level.INFO, "Initial Position: " + position.getLatitude() + ", " + position.getLongitude());

                    PoiLayer answer = new PoiLayer();
                    answer.addPoint(mapPoint, new Circle(8, Color.RED));

//                    positionProperty.addListener(e -> {
//                        Position pos = positionProperty.get();
//                        logger.log(Level.INFO, "New Position: " + pos.getLatitude() + ", " + pos.getLongitude());
//                        mapPoint.update(pos.getLatitude(), pos.getLongitude());
//                    });
                    return answer;
                })
                .orElseGet(() -> {
                    logger.log(Level.WARNING, "Position Service not available");
                    PoiLayer answer = new PoiLayer();
//                    mapPoint = new MapPoint(48.8567, 2.3508);
//                    answer.addPoint(mapPoint, new Circle(7, Color.RED));
                    return answer;
                });
    }
}