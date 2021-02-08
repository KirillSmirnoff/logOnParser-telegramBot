package init;

import java.io.*;
import java.util.Properties;

public class Init {
//   private static Properties properties;
   private final static Properties properties = new Properties();

   public static void start(){
       initProperties();
   }

    private static void initProperties() {
            try {
                properties.load(new BufferedReader(new InputStreamReader(Init.class.getResourceAsStream("/tags.txt"), "UTF-8")));
            } catch (IOException e) {
                System.out.println("ФАЙЛ СО ЗНАЧЕНИЯМИ ТЕГОВ НЕ НАЙДЕН\n");
                e.printStackTrace();
            }
    }

    public static Properties getProperties() {
        return properties;
    }
}
