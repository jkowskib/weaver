# Weaver
Simple XML parsing library for Java

```java
import jkowski.weaver.Tag;
import jkowski.weaver.XML;

public class ParserTest {
    public static void main(String[] args) {
        String exampleXML = """
                <note>
                  <to>Tove</to>
                  <from>Jani</from>
                  <heading>Reminder</heading>
                  <body>Don't forget me this weekend!</body>
                </note>
                """;
        
        // "note" is our root node and will be returned by the parser
        Tag xml = XML.parse(exampleXML);
        System.out.println(
                xml.getTag("to").content()
        );
    }
}
```