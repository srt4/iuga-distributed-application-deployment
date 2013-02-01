package info.iuga;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.util.concurrent.TimeUnit;


public class TestTorrent {
    public static void main(final String[] args) throws Exception {
        GameNightSettings.fetchSettings();

        final TorrentManager tm = new TorrentManager();

        new Thread() {
            public void run() {
                try {
                    while (true) {
                        System.out.print("Checking for new torrents...");
                        TorrentReceiver.addTorrentsFromJsonUrl(GameNightSettings.TORRENT_JSON_URL, tm);
                        System.out.println("done");
                        TimeUnit.SECONDS.sleep(GameNightSettings.REFRESH_INTERVAL);
                    }
                } catch (Exception e) {

                }
            }
        }.start();

        while (true) {
            System.out.println("===== Tracking " + tm.getClients().size() + " torrents =====");

            for (final Client client : tm.getClients()) {
                final SharedTorrent torrent = client.getTorrent();
                System.out.printf("%f %% - %d bytes downloaded - %d bytes uploaded\n", torrent.getCompletion(), torrent.getDownloaded(), torrent.getUploaded());
                System.out.println(torrent.getName());
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
