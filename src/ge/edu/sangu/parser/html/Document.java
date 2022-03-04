package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractStringParser;
import ge.edu.sangu.parser.IParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {

    public static final Pattern PATTERN_DOCTYPE = Pattern.compile("<!DOCTYPE\\s.+>", Pattern.CASE_INSENSITIVE);
    public static final Pattern PATTERN_HTML = Pattern.compile("<HTML.*>[\\S\\s]*</HTML>", Pattern.CASE_INSENSITIVE);

    private String doctypeDefinition;
    private Element rootHtmlElement;

    public String getDoctypeDefinition() {
        return doctypeDefinition;
    }

    public void setDoctypeDefinition(String doctypeDefinition) {
        this.doctypeDefinition = doctypeDefinition;
    }

    public Element getRootHtmlElement() {
        return rootHtmlElement;
    }

    public void setRootHtmlElement(Element rootHtmlElement) {
        this.rootHtmlElement = rootHtmlElement;
    }

    public static class Parser extends AbstractStringParser<Document> {

        @Override
        public Document parseString(String str) throws Exception {
            Document document = new Document();

            // Parse doctype definition (which should be the first line)
            Matcher doctypeMatcher = PATTERN_DOCTYPE.matcher(str);
            if (doctypeMatcher.find()) {
                document.setDoctypeDefinition(doctypeMatcher.group(0));
            }

            // Parse root html element
            Matcher htmlElementMatcher = PATTERN_HTML.matcher(str);
            if (htmlElementMatcher.find()) {
                Element.Parser htmlElementParser = new Element.Parser();
                Element htmlElement = htmlElementParser.parseString(htmlElementMatcher.group(0));
                document.setRootHtmlElement(htmlElement);
            }
            return document;
        }

    }
}
