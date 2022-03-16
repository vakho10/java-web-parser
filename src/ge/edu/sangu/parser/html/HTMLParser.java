package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractUrlParser;
import ge.edu.sangu.parser.IStringParser;

import java.util.regex.Pattern;

public class HTMLParser extends AbstractUrlParser<Document> {

    @Override
    public Document parse(byte[] bytes) throws Exception {
        return new Document.Parser().parse(bytes);
    }
}
