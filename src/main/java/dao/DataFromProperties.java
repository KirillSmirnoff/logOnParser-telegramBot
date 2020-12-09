package dao;

import java.io.*;
import java.util.Properties;

public class DataFromProperties {
    public Properties loadProperties() throws IOException {
        Properties properties = new Properties();
//        File file = new File("/home/k2/Desktop/IdeaProjects/IdeaProjects/test/LogOnelyaParser/src/main/resources/tags.txt");
//        File file = new File();
//        if (file.exists()) {
            properties.load(new BufferedReader(new InputStreamReader(DataFromProperties.class.getResourceAsStream("/tags.txt"),"UTF-8")));
//            System.out.println("YEAH find");
//        } else
//            System.out.println("file not found");
        return properties;
    }


}

//tags.txt
//        src/main/resources/tags.txt
