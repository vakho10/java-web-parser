package ge.edu.sangu.parser;

import java.net.URL;

public interface IUrlParser<T> extends IParser<T> {

    T parseUrl(String url) throws Exception;

    T parseUrl(URL url) throws Exception;
}
