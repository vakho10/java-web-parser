package ge.edu.sangu.parser.json;

import ge.edu.sangu.parser.AbstractUrlParser;
import ge.edu.sangu.parser.html.model.Document;

public class JSONParser extends AbstractUrlParser<Document> {

    @Override
    public Document parse(byte[] bytes) {
        throw new IllegalStateException("Not implemented!");
    }
}
