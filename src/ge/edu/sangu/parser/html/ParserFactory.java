package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.IStringParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ParserFactory {

    public static final Pattern PATTERN_ELEMENT_START = Pattern.compile("[\\s\\S]<(\\w+).*>");

    public static IStringParser<? extends IElement> getParserFrom(String str) {
        Matcher elementStartMatcher = PATTERN_ELEMENT_START.matcher(str);
        if (elementStartMatcher.find()) {
            return new ElementObject.Parser();
        } else {
            return new ElementText.Parser();
        }
    }
}
