package ge.edu.sangu.parser;

public interface IParser<T> {

    T parse(byte[] bytes) throws Exception;

}
