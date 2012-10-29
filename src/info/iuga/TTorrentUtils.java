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
        /*URLConnection connection = url.openConnection();
        connection.addRequestProperty("Referer", "http://iuga.info");


        StringBuilder builder = new StringBuilder();
        ByteArrayOutputStream reader = new ByteArrayOutputStream(1); //connection.getInputStream());

        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }

        final byte[] torrent = builder.toString().getBytes();
        System.out.println(new String(torrent));

        return new SharedTorrentFromUrl(url, torrent, outDir);    */
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = url.openStream();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ((n = is.read(byteChunk)) > 0) {
                bais.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return new SharedTorrentFromUrl(
                url,
                bais.toByteArray(),
                outDir
        );
    }
}
