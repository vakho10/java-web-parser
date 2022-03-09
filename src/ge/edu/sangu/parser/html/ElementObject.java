package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractStringParser;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementObject implements IElement {

    private String name;
    private Map<String, String> attributes = new HashMap<>();
    private List<IElement> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<IElement> getChildren() {
        return children;
    }

    public void setChildren(List<IElement> children) {
        this.children = children;
    }

    public static class Parser extends AbstractStringParser<ElementObject> {

        public static final Pattern PATTERN_NAME = Pattern.compile("^<(\\w+)>");
        public static final Pattern PATTERN_ATTRIBUTES = Pattern.compile("^<\\w+\\s+(.*)>");
        public static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("([\\w-]+)=(\\\"[\\w\\s'-=]*\\\")");
        public static final Pattern PATTERN_VALUE = Pattern.compile("^<\\w+.*>([\\S\\s]*)</\\w+.*>$");
        public static final Pattern PATTERN_ELEMENTS = Pattern.compile("((<\\w+[^/]*</.*>)|(\\w+))");

        @Override
        public ElementObject parseString(String str) {
            var elementObject = new ElementObject();

            // Parse element name
            Matcher nameMatcher = PATTERN_NAME.matcher(str);
            if (nameMatcher.find()) {
                elementObject.setName(nameMatcher.group(0));
            }

            // Parser element attributes (all)
            String attributes = null;
            Matcher attributesMatcher = PATTERN_ATTRIBUTES.matcher(str);
            if (attributesMatcher.find()) {
                attributes = attributesMatcher.group(0);
            }

            // TODO add code that catches attributes without values!
            if (attributes != null && !attributes.isBlank()) {
                // Parse each attribute
                Matcher attributeMatcher = PATTERN_ATTRIBUTE.matcher(str);
                if (attributeMatcher.find()) {
                    attributeMatcher.results().forEach(matchResult -> {
                        elementObject.attributes.put(matchResult.group(0), matchResult.group(1));
                    });
                }
            }

            // Parse value
            String value = null;
            Matcher valueMatcher = PATTERN_VALUE.matcher(str);
            if (valueMatcher.find()) {
                value = valueMatcher.group(0);
            }

            if (value != null && !value.isBlank()) {
                // Parse each element
                Matcher elementsMatcher = PATTERN_ELEMENTS.matcher(str);
                if (elementsMatcher.find()) {
                    elementsMatcher.results().forEach(matchResult -> {
                        while (true) {
                            IElement element = null;
                            try {
                                element = new ElementObject.Parser().parseString(matchResult.group(0));
                                elementObject.children.add(element);
                            } catch (Exception e) {
                                // TODO log this as warning
                                e.printStackTrace();
                                break;
                            }
                            try {
                                element = new ElementText.Parser().parseString(matchResult.group(0));
                                elementObject.children.add(element);
                            } catch (Exception e) {
                                // TODO log this as warning
                                e.printStackTrace();
                                break;
                            }
                            break;
                        }
                    });
                }
            }
            return elementObject;
        }
    }
}
