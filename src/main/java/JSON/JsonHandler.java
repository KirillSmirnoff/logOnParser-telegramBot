package JSON;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import dao.OperationWithFiles;
import init.Init;
import org.json.XML;

import java.io.*;
import java.util.Properties;

import static com.fasterxml.jackson.core.JsonToken.*;

public class JsonHandler {
    public String xmlToJsonString(String xml) throws IOException {

        return XML.toJSONObject(xml).toString();
    }

    public String jsonHandlerFromFile(File file) throws IOException {
        OperationWithFiles operation = new OperationWithFiles();

        return convert(operation.fileToString(file));  //преобразуем содержимое файла в строку(xml) и после конвертируем в JSON
    }

    public String convert(String s) throws IOException {
        Properties properties = Init.getProperties();
        String tagName = null;

        String jsonString = xmlToJsonString(s);
        StringBuilder sb = new StringBuilder(jsonString);


        /*<БЛОК ОТВЕЧАЮШИЙ ЗА ПАРСИНГ JSON >*/
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createParser(jsonString);

        /*находит в JSON заголовок FIELD_NAME (xml-теги)
         * получает значени заголовка (xml-тег), после вычисляет начало и конец (расположение тега) в JSON файле
         * далее данное расположение (начало и конец) потребуются для замены тег-xml на значение */
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
                tagName = parser.getText();
                int startIndex = sb.indexOf(tagName); /*получем начальное расположение тега-xml в JSON файле*/
                int finalIndex = 0;
                if (parser.getText().length() == 1) { /*получаем конечное расположение тега-xml согласно условию*/
                    finalIndex = startIndex + 1;
                } else
                    finalIndex = startIndex + parser.getText().length();

                /* БЛОК ОТВЕЧАЮШИЙ ЗА ПОИСК знаяения по ТЕГу  */
                String property = properties.getProperty(tagName);
                sb.replace(startIndex, finalIndex, property);

                /*тестовая реализация для проверки JSON заголовков
         if (jsonToken != null && (jsonToken.equals(VALUE_STRING) || jsonToken.equals(VALUE_NUMBER_INT) || jsonToken.equals(VALUE_NUMBER_FLOAT))) {
         if (jsonToken != null && jsonToken.equals(FIELD_NAME)) {
         System.out.println(dataBase.getFromDB(parser.getText()));
*/
            }
        }
        return sb.toString();
    }

    /* Если нужно ЗАПИСать РЕЗУЛЬТАТ В ФАЙЛ*/

        /*   saveJsonToFile(sb.toString());  сохраняем новый JSON файл на комп
        * далее открываем данный файл
        *
        LocalFileAction.getDesktop().open(saveJsonToFile(sb.toString()));
        *
        */
}
