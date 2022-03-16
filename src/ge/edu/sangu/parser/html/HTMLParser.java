package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractUrlParser;
import ge.edu.sangu.parser.html.model.Document;

/**
 * <h1>I love parser</h1>
 * This is a class that parses any existing HTML resources on the web, or bytes.
 *
 * @author Vakhtangi Laluashvili
 * @version v1.0.0
 * @see Document
 * @since 2015-09-22
 */
public class HTMLParser extends AbstractUrlParser<Document> {

    /**
     * This method parses HTML document's bytes and gives us Document object as its representation.
     *
     * @param bytes a bytes of some HTML document.
     * @return a {@link Document} object that represents the HTML document.
     * @throws Exception if HTML bytes hadn't been parsed.
     * @see <a href="https://google.com">My other stuff</a>
     */
    @Override
    public Document parse(byte[] bytes) throws Exception {
        return new Document.Parser().parse(bytes);
    }

    /**
     * @deprecated Stupid method that we no longer use.
     */
    @Deprecated
    public void foo() {
    }
}
