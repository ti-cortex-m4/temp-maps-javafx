import com.gluonhq.attach.position.PositionService;
import demo.maps.javafx.location.MyPositionServiceImpl;

module demo.maps.javafx.location {

    requires javafx.base;
    requires com.gluonhq.attach.position;

    provides PositionService with MyPositionServiceImpl;

    exports demo.maps.javafx.location;
}