package location;

import com.gluonhq.attach.position.Parameters;
import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class PositionService2Impl implements PositionService {

    private final String[] URLS = {
            "http://checkip.amazonaws.com/",
            "https://ipv4.icanhazip.com/",
            "http://myexternalip.com/raw",
            "http://ipecho.net/plain",
    };

    private final DatabaseReader dbReader;

    public PositionService2Impl() {
        try {
            dbReader = new DatabaseReader.Builder(this.getClass().getResourceAsStream("/GeoLite2-City.mmdb")).build();
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
                return "position";
            }

            @Override
            public Position get() {
                return getLocation2();
            }
        };
    }

    @Override
    public Position getPosition() {
        return getLocation2();
    }

    @Override
    public void start() {
    }

    @Override
    public void start(Parameters parameters) {
    }

    @Override
    public void stop() {
    }

    private Position getLocation2() {
        try {
            InetAddress ipAddress = getInetAddress();
            CityResponse response = dbReader.city(ipAddress);
            double latitude = response.getLocation().getLatitude();
            double longitude = response.getLocation().getLongitude();
            return new Position(latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private InetAddress getInetAddress() throws Exception {
        for (String url : URLS) {
            try {
                return getInetAddress(url);
            } catch (Exception e) {
            }
        }
        throw new RuntimeException();
    }

    private InetAddress getInetAddress(String url) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            String ip = in.readLine();
            return InetAddress.getByName(ip);
        }
    }

}
