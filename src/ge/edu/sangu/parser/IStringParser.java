package ge.edu.sangu.parser;

public interface IStringParser<T> extends IParser<T> {

    T parseString(String str) throws Exception;
}
