import JSON.JsonHandler;
import dao.DataBase;
import dao.DataFromProperties;
import org.xml.sax.SAXException;
import XML.XmlHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Scanner;


public class Launch {

        public static final String UriString ="/home/k2/Desktop/Onelya/docs/P62G62_test_answerExample.xml";
//    public static final String UriString = "/home/k2/Desktop/Onelya/docs/P62G62_answerExample";

    public static void main(String[] args) throws IOException{
        File file = new File(UriString); //вркменно закаментил путь у xml файл
//        JsonFromXML jsonFromXML = new JsonFromXML();
        JsonHandler jsonHandler = new JsonHandler();
        DataFromProperties dbPRop = new DataFromProperties();
//        FileToString fileToString = new FileToString();

//        String s = jsonFromXML.xmlToJsonString(fileToString.fileToString(file));// временно закаментил метод, т.к. в нем используется xml ФАЙЛ
//        System.out.println(s);  // временно закаментил метод, т.к. в нем используется xml ФАЙЛ

//            jsonFromXML.saveToJsonFile(file);

//        String s = xmlConverter.xmlToJsonConverter(xml);
//        System.out.println(s);
//        System.out.println("Current dir " + System.getProperty("user.dir"));

/*метод используется, если данный поступают на станддартный ввод-вывод*/
        jsonHandler.jsonHandlerFromStandardIO();

/*метод используется, если данный считываются с файла
        jsonHandler.jsonHandlerFromFile(file);*/

//        dbPRop.loadProperties();

//        xmlParser();
    }

    private static void xmlParser() {
        XmlHandler xmlHandler = new XmlHandler();
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(UriString, xmlHandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    private static void readFromFileAndPersist(File src, String query) {
        DataBase dataBase = new DataBase();
        String[] values;
        try (Scanner scanner = new Scanner(src)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                values = line.split("%");
                dataBase.persist(query, startInizialization(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] startInizialization(String[] originValues) {
        String[] mustValues = {"not value", "not value", "not value", "not value"};
        for (int i = 0; i < originValues.length; i++) {
            mustValues[i] = originValues[i];
        }
        return mustValues;
    }
}