package location;

import com.gluonhq.attach.position.Parameters;
import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.logging.Logger;

public class MyPositionServiceImpl implements PositionService {

    private static final Logger logger = Logger.getLogger(MyPositionServiceImpl.class.getName());

    private final String[] URLS = {
            "http://checkip.amazonaws.com/",
            "https://ipv4.icanhazip.com/",
            "http://myexternalip.com/raw",
            "http://ipecho.net/plain",
    };

    private final DatabaseReader databaseReader;

    public MyPositionServiceImpl() {
        try {
            databaseReader = new DatabaseReader.Builder(this.getClass().getResourceAsStream("/GeoLite2-City.mmdb")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReadOnlyObjectProperty<Position> positionProperty() {
        return new ReadOnlyObjectPropertyBase<Position>() {
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
            InetAddress inetAddress = getInetAddress();
            logger.info("The IP address: " + inetAddress);
            CityResponse response = databaseReader.city(inetAddress);
            Location location = response.getLocation();
            logger.info("The location: " + location);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            return new Position(latitude, longitude);
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

    // InetAddress ipAddress = InetAddress.getLocalHost();
    private InetAddress getInetAddress() {
        for (String url : URLS) {
            try {
                return getInetAddress(url);
            } catch (Exception e) {
                logger.info("Impossible to get public IP with the provider: " + url);
            }
        }
        throw new RuntimeException("Impossible to get public IP");
    }

    private InetAddress getInetAddress(String url) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            String host = br.readLine();
            return InetAddress.getByName(host);
        }
    }
}
