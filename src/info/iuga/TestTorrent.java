package info.iuga;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


public class TestTorrent {

    public static void main(final String[] args) throws IOException, NoSuchAlgorithmException, URISyntaxException, InterruptedException {
        final TorrentManager tm = new TorrentManager();

        new Thread() {
            public void run() {
                try {
                    while (true) {
                        System.out.println("Checked for new torrents.");
                        TorrentReceiver.getTorrentsFromJson("http://iuga.info/gamenight/torrents.php", tm);
                        TimeUnit.SECONDS.sleep(10);
                    }
                } catch (Exception e) {

                }
            }
        }.start();

        while (true) {
            for (final Client client : tm.getClients()) {
                final SharedTorrent torrent = client.getTorrent();
                System.out.printf("%f %% - %d bytes downloaded - %d bytes uploaded\n", torrent.getCompletion(), torrent.getDownloaded(), torrent.getUploaded());
                System.out.println(torrent.getName());
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
