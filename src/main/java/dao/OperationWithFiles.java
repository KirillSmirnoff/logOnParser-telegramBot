package dao;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class OperationWithFiles {
    public File saveResultToFile(String jsonString) throws IOException {
//        File jsonFile = Paths.get(System.getProperty("user.dir") + "/JSON_Answer.json").toFile();
        File jsonFile = Paths.get("/home/k2/IdeaProjects/LogOnelyaParser/src/main/resources/json_temp_file").toFile();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(jsonFile), "UTF-8");

        writer.write(jsonString);
        writer.flush();
        writer.close();

        return jsonFile;
    }

    public String fileToString(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = new byte[10];
        while (fileInputStream.read(bytes) != -1){
            stringBuilder.append(new String(bytes));
            bytes = new byte[10];
        }
        fileInputStream.close();
        return stringBuilder.toString();
    }
}
