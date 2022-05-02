package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class ParserMDTest {
    @Test
    public void testParsingHasKeys() {
        String meta =
                "firstName: \"John\"\n"
                        + "lastName: \"Doe\"\n"
                        + "age: 20\n"
                        + "---\n"
                        + "This is the content";
        Reader r = new StringReader(meta);
        BufferedReader br = new BufferedReader(r);

        var map = ParserContentFile.parse(br);
        assertNotNull(map.get("firstName"));
        assertNotNull(map.get("lastName"));
        assertNotNull(map.get("age"));
        assertNotNull(map.get("content"));
    }

    @Test
    public void testParsingSplitbetweenYamlAndContent() {
        String meta =
                "firstName: \"John\"\n"
                        + "lastName: \"Doe\"\n"
                        + "age: 20\n"
                        + "---\n"
                        + "This is the content";
        Reader r = new StringReader(meta);
        BufferedReader br = new BufferedReader(r);

        var map = ParserContentFile.parse(br);
        assertEquals("John", map.get("firstName"));
        assertEquals("Doe", map.get("lastName"));
        assertEquals(20, map.get("age"));
        assertEquals("This is the content", map.get("content"));
    }

    @Test
    public void testParsingWithNoMetaData() {
        String fileContent = "---\nThis is the content\n but there isnt any meta data";
        Reader r = new StringReader(fileContent);
        BufferedReader br = new BufferedReader(r);
        var map = ParserContentFile.parse(br);
        assertEquals("This is the content\n but there isnt any meta data", map.get("content"));
        assertEquals(1, map.keySet().size());
    }

    @Test
    public void testParsingWithNoDelimiterIsConsideredAsContentWithNoMetadata() {
        String fileContent = "firstName: \"John\"\n" + "lastName: \"Doe\"\n" + "age: 20\n";
        Reader r = new StringReader(fileContent);
        BufferedReader br = new BufferedReader(r);
        var map = ParserContentFile.parse(br);
        assertTrue(map.containsKey("content"));
        assertEquals(1, map.keySet().size());
        assertEquals(
                "firstName: \"John\"\n" + "lastName: \"Doe\"\n" + "age: 20", map.get("content"));
    }

    @Test
    public void testParsingComlexYaml() {
        String fileContent =
                "employees:\n"
                        + "    - \n"
                        + "        id: 213\n"
                        + "        name: franc\n"
                        + "        others:\n"
                        + "            - { department: sales, did: 1} \n"
                        + "            - { salary: 5000}\n"
                        + "            - { address: USA, pincode: 97845 }\n"
                        + "---\n"
                        + "This is the content";
        Reader r = new StringReader(fileContent);
        BufferedReader br = new BufferedReader(r);
        var map = ParserContentFile.parse(br);
        System.out.println(map);

        System.out.println(map.get("employees"));
        assertTrue(map.containsKey("employees"));
        assertTrue(map.containsKey("content"));
    }
}
