package info.iuga;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

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
        synchronized (CLIENT_LIST) {
            return Collections.unmodifiableList(CLIENT_LIST);
        }
    }

    public void postStatusToUrl(final String uri) throws Exception {
        // taken from http://www.javaworld.com/javatips/jw-javatip34.html
        // this is really, really fugly
        URL url;
        URLConnection urlConn;
        DataOutputStream printout;
        DataInputStream input;
        // URL of CGI-Bin script.
        url = new URL (uri);
        // URL connection channel.
        urlConn = url.openConnection();
        // Let the run-time system (RTS) know that we want input.
        urlConn.setDoInput (true);
        urlConn.setDoOutput(true);

        // Send POST output.
        printout = new DataOutputStream (urlConn.getOutputStream ());
        String content =
                "ip=" + InetAddress.getLocalHost().toString() +
                "&data=" + URLEncoder.encode(this.toString());
        printout.writeBytes (content);
        printout.flush ();
        printout.close ();


        // Get response data.
        input = new DataInputStream (urlConn.getInputStream ());
        String str;
        while (null != ((str = input.readLine())))
        {
            System.out.println("Server said >> " + str);
        }
        input.close();
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for(final Client client : this.getClients()) {
            final SharedTorrent torrent = client.getTorrent();
            sb.append(torrent.getName() + "=" + torrent.getCompletion() + "^");
        }

        return sb.toString();
    }
}
