package ge.edu.sangu.parser;

import ge.edu.sangu.parser.html.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public abstract class AbstractUrlParser<T> implements IUrlParser<T> {

    @Override
    public T parseUrl(String url) throws Exception {
        return parseUrl(URI.create(url).toURL());
    }

    @Override
    public T parseUrl(URL url) throws Exception {
        byte[] bytes = downloadResource(url);
        return parse(bytes);
    }

    private byte[] downloadResource(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        try (var inputStream = connection.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }
}
