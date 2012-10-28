import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Peer;
import com.turn.ttorrent.common.Torrent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class TestTorrent {
    private static final String TORRENT_URL = "/Users/spencer/Downloads/test.torrent";
    private static final String TORRENT_SAVE_DIR = "/Users/spencer/tmp";

    public static void main(final String[] args) throws IOException, NoSuchAlgorithmException, URISyntaxException, InterruptedException {
        TestTorrent t = new TestTorrent();
    }

    public TestTorrent() throws IOException, NoSuchAlgorithmException, URISyntaxException, InterruptedException {

        final SharedTorrent torrent = SharedTorrent.fromFile(
                new File(TORRENT_URL),
                new File(TORRENT_SAVE_DIR)
        );

        final Client client = new Client(
                // This is the interface the client will listen on (you might need something
                // else than localhost here).
                InetAddress.getLocalHost(),
                torrent

                // Load the torrent from the torrent file and use the given
                // output directory. Partials downloads are automatically recovered.

        );

        try {
            System.out.println("Starting client for torrent: "+torrent.getName());

            try {
                System.out.println("Start to download: "+torrent.getName());
                client.share(); // SEEDING for completion signal
                // client.download()    // DONE for completion signal

                while (!Client.ClientState.SEEDING.equals(client.getState())) {
                    // Check if there's an error
                    if (Client.ClientState.ERROR.equals(client.getState())) {
                        throw new Exception("ttorrent client Error State");
                    }

                    // Display statistics
                    System.out.printf("%f %% - %d bytes downloaded - %d bytes uploaded\n", torrent.getCompletion(), torrent.getDownloaded(), torrent.getUploaded());
                    // Wait one second
                    TimeUnit.SECONDS.sleep(1);
                }

                System.out.println("download completed.");
            } catch (Exception e) {
                System.err.println("An error occurs...");
                e.printStackTrace(System.err);
            } finally {
                System.out.println("stop client.");
                client.stop();
            }
        } catch (Exception e) {
            System.err.println("An error occurs...");
            e.printStackTrace(System.err);
        }
    }
}
