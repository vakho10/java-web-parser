package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractUrlParser;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser extends AbstractUrlParser<Document> {

    public static final Pattern PATTERN_TITLE = Pattern.compile("<title>(.*)</title>");

    @Override
    public Document parse(byte[] bytes) throws Exception {
        return new Document.Parser().parse(bytes);
    }

    // TODO remove this method!
    private String extractTitleFrom(byte[] documentBytes) {
        String documentStr = new String(documentBytes, Charset.forName("UTF-8"));
        Matcher matcher = PATTERN_TITLE.matcher(documentStr);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
