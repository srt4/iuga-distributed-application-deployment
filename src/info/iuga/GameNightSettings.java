package info.iuga;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GameNightSettings {
    public static String TORRENT_JSON_URL;
    public static File SAVE_DIR;
    public static Integer REFRESH_INTERVAL;
    public static String STATUS_URL;

    private static final String SETTINGS_URL = "http://www.iuga.info/gamenight/config.php";

    public static void fetchSettings() throws Exception {
        final URL url = new URL(SETTINGS_URL);
        final URLConnection connection = url.openConnection();
        connection.addRequestProperty("Referer", "http://iuga.info");

        final StringBuilder builder = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        final JSONParser parser = new JSONParser();
        final JSONObject jsonPointer = (JSONObject) parser.parse(builder.toString());

        final String torrentJsonUrl = jsonPointer.get("torrentUrl").toString();
        final String saveDir = jsonPointer.get("saveDir").toString();
        final Integer refreshInterval = Integer.parseInt(
                jsonPointer.get("refreshInterval").toString()
        );
        final String statusUrl = jsonPointer.get("statusUrl").toString();

        TORRENT_JSON_URL = torrentJsonUrl;
        SAVE_DIR = new File(saveDir);
        REFRESH_INTERVAL = refreshInterval;
        STATUS_URL = statusUrl;

        System.out.println("Saving to " + SAVE_DIR.getAbsolutePath());
        System.out.println("Refreshing torrents every " + REFRESH_INTERVAL + " seconds.");
        System.out.println("Retrieving them from " + TORRENT_JSON_URL);
    }
}
