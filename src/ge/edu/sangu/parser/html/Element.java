package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractStringParser;

import java.util.List;
import java.util.Map;

public class Element {

    private String name;
    private ElementType type;
    private Map<String, String> attributes;
    private List<Element> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<Element> getChildren() {
        return children;
    }

    public void setChildren(List<Element> children) {
        this.children = children;
    }

    public static class Parser extends AbstractStringParser<Element> {

        @Override
        public Element parseString(String str) {
            return new Element(); // TODO implements this!
        }
    }
}
