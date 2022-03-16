package ge.edu.sangu.parser.html.model;

import ge.edu.sangu.parser.AbstractStringParser;

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

        public static final Pattern PATTERN_NAME = Pattern.compile("^<(\\w+).*>");
        public static final Pattern PATTERN_ATTRIBUTES = Pattern.compile("^<\\w+\\s+(.*)>");
        public static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("([\\w-]+)=\\\"([\\w\\s'-=]*)\\\"");
        public static final Pattern PATTERN_VALUE = Pattern.compile("^<\\w+.*>([\\S\\s]*)</\\w+.*>$");
        public static final Pattern PATTERN_ELEMENT_START = Pattern.compile("[\\s\\S](<(\\w+)[\\s\\w\\\"\\=\\-\\,\\/\\;\\&\\.]*>)");

        @Override
        public ElementObject parseString(String str) {
            var elementObject = new ElementObject();

            // Extract tag name
            extractName(str, elementObject);

            // Extract tag attributes
            extractAttributes(str, elementObject);

            // Extract tag children (recursively)
            extractChildren(str, elementObject);

            return elementObject;
        }

        private void extractChildren(String str, ElementObject elementObject) {
            Matcher valueMatcher = PATTERN_VALUE.matcher(str);
            if (valueMatcher.find()) {
                String value = valueMatcher.group(1);

                List<String> foundSubValues = new ArrayList<>();
                String nextSubValue = value;
                while (true) {
                    Matcher elementStartMatcher = PATTERN_ELEMENT_START.matcher(nextSubValue);
                    if (!elementStartMatcher.find()) {
                        break;
                    }
                    String fullTagValue = elementStartMatcher.group(1);
                    String tagName = elementStartMatcher.group(2);
                    String tagEndName = String.format("</%s>", tagName);
                    int tagNameIndex = nextSubValue.indexOf(tagName);
                    int tagStart = tagNameIndex - 1;
                    int indexOfEndName = nextSubValue.indexOf(tagEndName);
                    if (indexOfEndName == -1) {
                        foundSubValues.add(fullTagValue);
                        nextSubValue = nextSubValue.substring(tagStart + fullTagValue.length() - tagName.length() - 1);
                    } else {
                        int tagEnd = indexOfEndName + tagEndName.length();
                        foundSubValues.add(nextSubValue.substring(tagStart, tagEnd));
                        nextSubValue = nextSubValue.substring(tagEnd);
                    }
                }

                // If element object not found
                if (foundSubValues.isEmpty()) {
                    elementObject.children.add(new ElementText.Parser().parseString(value));
                } else {
                    // TODO Needs to implement scenario where middle element is text element
                    for (String foundSubValue : foundSubValues) {
                        elementObject.children.add(new Parser().parseString(foundSubValue));
                    }
                }
            }
        }

        private void extractAttributes(String str, ElementObject elementObject) {
            String attributes = null;
            Matcher attributesMatcher = PATTERN_ATTRIBUTES.matcher(str);
            if (attributesMatcher.find()) {
                attributes = attributesMatcher.group(1);
            }

            if (attributes != null && !attributes.isBlank()) {
                // Parse each attribute
                Matcher attributeMatcher = PATTERN_ATTRIBUTE.matcher(attributes);
                while (attributeMatcher.find()) {
                    elementObject.attributes.put(attributeMatcher.group(1), attributeMatcher.group(2));
                }
            }
        }

        private void extractName(String str, ElementObject elementObject) {
            Matcher nameMatcher = PATTERN_NAME.matcher(str);
            if (nameMatcher.find()) {
                elementObject.setName(nameMatcher.group(1));
            }
        }
    }
}
