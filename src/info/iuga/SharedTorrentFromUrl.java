package info.iuga;

import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class SharedTorrentFromUrl extends SharedTorrent {
    private URL url;

    public SharedTorrentFromUrl(final URL url, final byte[] torrent, final File destDir) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        super(torrent, destDir);
        this.url = url;
    }

    public SharedTorrentFromUrl(Torrent torrent, File destDir) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        super(torrent, destDir);
    }

    public SharedTorrentFromUrl(Torrent torrent, File destDir, boolean seeder) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        super(torrent, destDir, seeder);
    }

    public SharedTorrentFromUrl(byte[] torrent, File destDir) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        super(torrent, destDir);
    }

    public SharedTorrentFromUrl(byte[] torrent, File parent, boolean seeder) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        super(torrent, parent, seeder);
    }

    public URL getUrl() {
        return url;
    }
}
