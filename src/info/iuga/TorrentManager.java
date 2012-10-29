package info.iuga;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TorrentManager {
    private final List<Client> CLIENT_LIST = new ArrayList<Client>();

    public void addTorrent(final SharedTorrent torrent) {
        try {
            final Client c = new Client(
                    InetAddress.getLocalHost(),
                    torrent
            );
            c.share();
            CLIENT_LIST.add(c);
        } catch (final Exception e) {
            System.out.println("Unable to add torrent. " + e.getMessage());
        }
    }

    public void stopTorrenting() {
        for (final Client c : CLIENT_LIST) {
            c.stop();
        }
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(CLIENT_LIST);
    }
}
