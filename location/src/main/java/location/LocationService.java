package location;

import com.gluonhq.attach.position.Position;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class LocationService {

    private final DatabaseReader dbReader;

    public LocationService()  {
        try {
            dbReader = new DatabaseReader.Builder(this.getClass().getResourceAsStream("/GeoLite2-City.mmdb")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InetAddress getInetAddress()  throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(            new URL("http://checkip.amazonaws.com").openStream()))) {
            String ip = in.readLine();
            return InetAddress.getByName(ip);
        }
    }

//    http://checkip.amazonaws.com/
//    https://ipv4.icanhazip.com/ (works with ipv6 as subdomain too!)
//    http://myexternalip.com/raw
//    http://ipecho.net/plain

    public IP() {
        URL ipAdress;

        try {
            ipAdress = new URL("http://myexternalip.com/raw");

            BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));

            String ip = in.readLine();
            System.out.println(ip);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Position getLocation() throws Exception {
        InetAddress ipAddress =  getInetAddress();
        CityResponse response = dbReader.city(ipAddress);
        double latitude = response.getLocation().getLatitude();
        double longitude = response.getLocation().getLongitude();
        return new Position(latitude, longitude);
    }
}
