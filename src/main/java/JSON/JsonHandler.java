package JSON;

import IO.FileToString;
import IO.LocalFileAction;
import IO.StandardInToString;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import dao.DataBase;
import dao.DataFromProperties;
import org.json.XML;

import java.io.*;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;

import static com.fasterxml.jackson.core.JsonToken.*;

public class JsonHandler {

    public String xmlToJsonString(String xml) throws IOException {

        return XML.toJSONObject(xml).toString();
    }

    public File saveToJsonFile(String jsonString) throws IOException {
        FileToString fileToString = new FileToString();
//        File jsonFile = Paths.get(file.toPath().toString() + Instant.now().toEpochMilli() / 1000 + ".json").toFile();
//        File jsonFile = Paths.get(System.getProperty("user.dir")+ "/logOnelyaJsonAnswer"+Instant.now().toEpochMilli() / 1000 + ".json").toFile();
        File jsonFile = Paths.get(System.getProperty("user.dir")+ "/JSON_Answer.json").toFile();
//        System.getProperty("user.dir");
//        String s = xmlToJsonString(fileToString.fileToString(file));


//        FileWriter writer = new FileWriter(jsonFile);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(jsonFile),"UTF-8");
        writer.write(jsonString);
        writer.flush();
        writer.close();

        return jsonFile;
    }

    public void jsonHandlerFromFile(File file) throws IOException {
        FileToString fiToS = new FileToString();
        DataBase dataBase = new DataBase();

        String jsonString = xmlToJsonString(fiToS.fileToString(file));  //преобразуем содержимое файла в строку(xml) и после конвертируем в JSON
//        System.out.println(jsonString);

        StringBuilder sb = new StringBuilder(jsonString);
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createParser(jsonString);

//        while(!parser.isClosed()) {
//            JsonToken jsonToken = parser.nextToken();
//
//            System.out.println("jsonToken = " + jsonToken+"   "+parser.getText());
//        }

        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
                int iSt = sb.indexOf(parser.getText());
                int iFl = 0;
                if (parser.getText().length()==1) {
                    iFl = iSt+1;
                }
                else
                    iFl = iSt + parser.getText().length();
                sb.replace(iSt,iFl, dataBase.getFromDB(parser.getText()));

//            if (jsonToken != null && (jsonToken.equals(VALUE_STRING) || jsonToken.equals(VALUE_NUMBER_INT) || jsonToken.equals(VALUE_NUMBER_FLOAT))) {
//            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
//                System.out.println(dataBase.getFromDB(parser.getText()));
            }
        }
        saveToJsonFile(sb.toString()); // сохраняем новый JSON файл на комп
    }

    public void jsonHandlerFromStandardIO() throws IOException {
        StandardInToString sIo = new StandardInToString();
        DataBase dataBase = new DataBase();
        DataFromProperties dbProp = new DataFromProperties();
        String s = sIo.inToString();
        String jsonString = xmlToJsonString(s);  //преобразуем переданные данные строка(xml)  в JSON
//        System.out.println(jsonString);

        StringBuilder sb = new StringBuilder(jsonString);
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createParser(jsonString);

//        while(!parser.isClosed()) {
//            JsonToken jsonToken = parser.nextToken();
//
//            System.out.println("jsonToken = " + jsonToken+"   "+parser.getText());
//        }
//
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
                int iSt = sb.indexOf(parser.getText());
                int iFl = 0;
                if (parser.getText().length()==1) {
                    iFl = iSt+1;
                }
                else
                    iFl = iSt + parser.getText().length();
//                sb.replace(iSt,iFl, dataBase.getFromDB(parser.getText()));
                Properties properties = dbProp.loadProperties();
                String text = parser.getText();
                String tagmean = properties.getProperty(text);
                sb.replace(iSt,iFl, tagmean);

//            if (jsonToken != null && (jsonToken.equals(VALUE_STRING) || jsonToken.equals(VALUE_NUMBER_INT) || jsonToken.equals(VALUE_NUMBER_FLOAT))) {
//            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
//                System.out.println(dataBase.getFromDB(parser.getText()));
            }
        }
//        System.out.println(sb.toString());
        File file = saveToJsonFile(sb.toString());// сохраняем новый JSON файл на комп
//        LocalFileAction.getDesktop().open(Paths.get(System.getProperty("user.dir")+ "/JSON_Answer.json").toFile());
        LocalFileAction.getDesktop().open(file);
    }
}
