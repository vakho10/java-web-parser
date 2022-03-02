package ge.edu.sangu;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebParser {

    public static final Pattern PATTERN_TITLE = Pattern.compile("<title>(.*)</title>");

    public static Document parseUrl(String url) throws IOException {
        return parseUrl(URI.create(url).toURL());
    }

    public static Document parseUrl(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        try (var inputStream = connection.getInputStream()) {
            byte[] dataBytes = inputStream.readAllBytes();
            Document document = new Document();
            document.setDataBytes(dataBytes);
            document.setTitle(extractTitleFrom(dataBytes));
            return document;
        }
    }

    private static String extractTitleFrom(byte[] documentBytes) {
        // TODO We may get different charset webpage
        String documentStr = new String(documentBytes, Charset.forName("UTF-8"));
        Matcher matcher = PATTERN_TITLE.matcher(documentStr);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
