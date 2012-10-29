package info.iuga;

import com.turn.ttorrent.client.SharedTorrent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class SharedTorrentFromUrl extends SharedTorrent {
    private URL url;

    public SharedTorrentFromUrl(final URL url, final byte[] torrent, final File destDir) throws IOException, NoSuchAlgorithmException {
        super(torrent, destDir);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}
