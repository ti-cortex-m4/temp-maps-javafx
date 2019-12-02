package demo.maps.javafx.location;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.logging.Logger;

class MyIpService {

    private static final Logger logger = Logger.getLogger(MyIpService.class.getName());

    private static final String[] URLS = {
            "http://checkip.amazonaws.com/",
            "https://ipv4.icanhazip.com/",
            "http://myexternalip.com/raw",
            "http://ipecho.net/plain",
    };

    public InetAddress getIp() {
        for (String url : URLS) {
            try {
                return getIp(url);
            } catch (Exception e) {
                logger.info("Impossible to get the public IP by: " + url);
            }
        }
        throw new RuntimeException("Impossible to get the public IP");
    }

    private InetAddress getIp(String url) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            String host = br.readLine();
            return InetAddress.getByName(host);
        }
    }
}
