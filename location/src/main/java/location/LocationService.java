package location;

import com.gluonhq.attach.position.Position;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.IOException;
import java.net.InetAddress;

public class LocationService {

    private final DatabaseReader dbReader;

    public LocationService()  {
//        File database = new File("");
        try {
            dbReader = new DatabaseReader.Builder(this.getClass().getResourceAsStream("/GeoLite2-City.mmdb")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Position getLocation() throws IOException, GeoIp2Exception {
        InetAddress ipAddress =  InetAddress. getLocalHost();
        CityResponse response = dbReader.city(ipAddress);
        double latitude = response.getLocation().getLatitude();
        double longitude = response.getLocation().getLongitude();
        return new Position(latitude, longitude);
    }
}
