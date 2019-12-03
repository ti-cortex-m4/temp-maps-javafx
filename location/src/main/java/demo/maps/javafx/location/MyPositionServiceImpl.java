package demo.maps.javafx.location;

import com.gluonhq.attach.position.Parameters;
import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.logging.Logger;

public class MyPositionServiceImpl implements PositionService {

    private static final Logger logger = Logger.getLogger(MyPositionServiceImpl.class.getName());

    private final MyIpService myIpService = new MyIpService();
    private final DatabaseReader databaseReader;

    public MyPositionServiceImpl() {
        try (InputStream is = this.getClass().getResourceAsStream("/GeoLite2-City.mmdb")) {
            databaseReader = new DatabaseReader.Builder(is).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReadOnlyObjectProperty<Position> positionProperty() {
        return new ReadOnlyObjectPropertyBase<>() {
            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public Position get() {
                return getPosition();
            }
        };
    }

    @Override
    public Position getPosition() {
        try {
            InetAddress ip = myIpService.getIp();
            logger.info("The IP: " + ip);
            CityResponse response = databaseReader.city(ip);
            Location location = response.getLocation();
            logger.info("The location: " + location);
            return new Position(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        logger.info("The service has started: " + this);
    }

    @Override
    public void start(Parameters parameters) {
        logger.info("The service has started: " + this + " with parameters: " + parameters);
    }

    @Override
    public void stop() {
        logger.info("The service has stopped: " + this);
    }
}
