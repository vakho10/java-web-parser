package ge.edu.sangu;

import ge.edu.sangu.parser.html.HTMLParser;

public class DemoTest {

    public static void main(String[] args) {
        try {
            var htmlParser = new HTMLParser();
            var document = htmlParser.parseUrl("https://docs.oracle.com/en/java/javase/17/docs/api/index.html");
            System.out.println(document.getDoctypeDefinition());
            System.out.println(document.getRootHtmlElement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
