package JSON;

import IO.FileToString;
import org.json.XML;

import java.io.*;
import java.nio.file.Paths;
import java.time.Instant;

public class JsonFromXML {

    public String xmlToJsonString(String xml) throws IOException {

        return XML.toJSONObject(xml).toString();
    }

    public File saveToJsonFile(File file) throws IOException {
        FileToString fileToString = new FileToString();
        File jsonFile = Paths.get(file.toPath().toString() +
                Instant.now().toEpochMilli() / 1000 +
                ".json").toFile();

        String s = xmlToJsonString(fileToString.fileToString(file));

        FileWriter writer = new FileWriter(jsonFile);
        writer.write(s);
        writer.flush();
        writer.close();

        return jsonFile;
    }
}
