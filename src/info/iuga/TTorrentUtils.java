package info.iuga;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class TTorrentUtils {
    public static SharedTorrentFromUrl createSharedTorrentFromUrl(
            final URL url,
            final File outDir
    ) throws IOException, NoSuchAlgorithmException {
        final ByteArrayOutputStream bais = new ByteArrayOutputStream();

        final InputStream is = url.openStream();
        byte[] byteChunk = new byte[4096];

        int n;
        while ((n = is.read(byteChunk)) > 0) {
            bais.write(byteChunk, 0, n);
        }
        is.close();

        return new SharedTorrentFromUrl(
                url,
                bais.toByteArray(),
                outDir
        );
    }
}
