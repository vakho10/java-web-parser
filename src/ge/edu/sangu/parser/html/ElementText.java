package ge.edu.sangu.parser.html;

import ge.edu.sangu.parser.AbstractStringParser;

public class ElementText implements IElement {

    private String value;

    public ElementText() {
    }

    public ElementText(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class Parser extends AbstractStringParser<ElementText> {

        @Override
        public ElementText parseString(String str) {
            return new ElementText(str);
        }
    }
}
