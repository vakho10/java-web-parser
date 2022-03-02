package ge.edu.sangu;

import java.io.IOException;

public class DemoTest {

    public static void main(String[] args) {
        try {
            Document document = WebParser.parseUrl("https://docs.oracle.com/en/java/javase/17/docs/api/index.html");
            System.out.println(document.getDataBytes().length);
            System.out.println("TITLE: " + document.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
