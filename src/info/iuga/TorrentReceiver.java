package info.iuga;

import com.sun.istack.internal.Nullable;
import com.turn.ttorrent.client.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;

/**
 * Polls a server for new torrents
 *
 * @author Spencer <srt4@uw.edu>
 */
public class TorrentReceiver {
    public static void getTorrentsFromJson(
            final String urlString,
            final TorrentManager manager
    ) throws NoSuchAlgorithmException {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Referer", "http://iuga.info");


            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            final JSONParser parser = new JSONParser();
            JSONObject jsonPointer = (JSONObject) parser.parse(builder.toString());


            // Grab the URLs from the JSON received from the server
            for (Object torrent : (JSONArray) jsonPointer.get("responseData")) {
                final String torrentUrl = ((JSONObject) torrent).get("url").toString();

                final URL tUrl = new URL(torrentUrl);

                boolean addTorrent = true;
                for (final Client client : manager.getClients()) {
                    final SharedTorrentFromUrl torr = (SharedTorrentFromUrl) client.getTorrent();
                    @Nullable final URL otherUrl = torr.getUrl();
                    addTorrent = addTorrent && !tUrl.sameFile(otherUrl);
                }

                // Could be replaced by a simple break in the above for loop
                if (addTorrent) {
                    final SharedTorrentFromUrl torrentToAdd = TTorrentUtils.createSharedTorrentFromUrl(
                            tUrl,
                            GameNightSettings.SAVE_DIR
                    );
                    manager.addTorrent(
                            torrentToAdd
                    );
                }
            }

        } catch (final MalformedURLException e) {
            System.out.println("Malformed URL passed.");
        } catch (final IOException e) {
            System.out.println("Could not connect to URL specified.");
            e.printStackTrace();
        } catch (final ParseException e) {
            System.out.println("JSON was malformed.");
        }
    }
}
