package utils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;

/***
 * This class is used to parse the reader, it split the content at ---
 * then it parse as yaml the first half and keep the rest as a string
 * If there is no --- it will return the whole as the content
 */
public class ParserContentFile {
    public static HashMap<String, Object> parse(BufferedReader reader) {
        if (reader == null) {
            return new HashMap<>();
        }
        StringBuilder sb = new StringBuilder();
        String yaml = null;
        String content = null;
        boolean isYaml = true;
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                // skip blanck lines only when parsing yaml
                if (line.isBlank() && isYaml) continue;
                if (line.equals("---")) {
                    isYaml = false;
                    yaml = sb.toString();
                    sb.delete(0, sb.length());
                    continue;
                }
                sb.append(line).append("\n");
            }
            // delete the last \n
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        content = sb.toString();
        HashMap<String, Object> documentMap = new HashMap<>();
        if (yaml != null) {
            var mapYaml = (HashMap<String, Object>) new Yaml().load(new StringReader(yaml));
            if (mapYaml != null) {
                documentMap.putAll(mapYaml);
            }
        }
        if (!content.trim().isBlank()) {
            documentMap.put("content", content);
        }
        return documentMap;
    }
}
