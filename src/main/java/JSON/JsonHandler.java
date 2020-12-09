package JSON;

import IO.FileToString;
import IO.LocalFileAction;
import IO.ReadDataFromStandardIO;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import dao.DataBase;
import dao.DataFromProperties;
import org.json.XML;

import java.io.*;
import java.nio.file.Paths;
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

/*метод используется, если данный поступают на станддартный ввод-вывод*/
    public void jsonHandlerFromStandardIO() throws IOException {
        ReadDataFromStandardIO sIo = new ReadDataFromStandardIO();
        DataBase dataBase = new DataBase();
        DataFromProperties dbProp = new DataFromProperties();
        Properties properties = dbProp.loadProperties();
        String tagName = null;

        String xmlString = sIo.inToString();    /*преобразуем данные со стандартного ввода в строку - String*/
        String jsonString = xmlToJsonString(xmlString);  /*преобразуем переданные данные строка-XML  в JSON*/

        StringBuilder sb = new StringBuilder(jsonString);

        /*<БЛОК ОТВЕЧАЮШИЙ ЗА ПАРСИНГ JSON ФАЙЛА>*/
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createParser(jsonString);

        /*находит в JSON заголовок FIELD_NAME
        * получает значени заголовка (тег-xml), и вычисляет начало и конец (расположение) в JSON файле
        * далее данное расположение (начало и конец) потребуются для замены тег-xml на значение данного из БД*/
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
                tagName = parser.getText();
                int startIndex = sb.indexOf(tagName); /*получем начальное расположение тега-xml в JSON файле*/
                int finalIndex = 0;
                    if (parser.getText().length()==1) { /*получаем конечное расположение тега-xml согласно условию*/
                        finalIndex = startIndex+1;
                     }
                    else
                        finalIndex = startIndex + parser.getText().length();

                /* БЛОК ОТВЕЧАЮШИЙ ЗА ПОИСК ТЕГА В БД ИЛИ БАЗЕ-ЗНАНИЙ(ФАЙЛ) */

                /*метод считывает значение тега-xml из БД
             sb.replace(startIndex,finalIndex, dataBase.getFromDB(parser.getText()));
                */

                /*метод считывает значения тега-xml из БазыЗнаний(файл)*/
                sb.replace(startIndex,finalIndex, properties.getProperty(tagName));

                /*тестовая реализация для проверки JSON заголовков
//            if (jsonToken != null && (jsonToken.equals(VALUE_STRING) || jsonToken.equals(VALUE_NUMBER_INT) || jsonToken.equals(VALUE_NUMBER_FLOAT))) {
//            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
//                System.out.println(dataBase.getFromDB(parser.getText()));
*/
            }
        }
        File file = saveToJsonFile(sb.toString());// сохраняем новый JSON файл на комп
//        LocalFileAction.getDesktop().open(Paths.get(System.getProperty("user.dir")+ "/JSON_Answer.json").toFile());
        LocalFileAction.getDesktop().open(file);
    }
}
