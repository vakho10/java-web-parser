package ge.edu.sangu.parser;

import java.nio.charset.StandardCharsets;

public abstract class AbstractStringParser<T> implements IStringParser<T> {

    @Override
    public T parse(byte[] bytes) throws Exception {
        return parseString(new String(bytes, StandardCharsets.UTF_8));
    }
}
