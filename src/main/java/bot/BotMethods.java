package bot;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class BotMethods {
    public void getFile(Update update) throws IOException {
        String file_id = update.getMessage().getDocument().getFileId();

        String file_path = getFileInfo(file_id);

        URL urlDownloadFile = new URL("https://api.telegram.org/file/bot<token>/"+file_path);
        InputStream in = urlDownloadFile.openStream();

        ReadableByteChannel readableByteChannel = Channels.newChannel(in);
        FileOutputStream file = new FileOutputStream("/home/k2/IdeaProjects/LogOnelyaParser/src/main/resources/xml_file");
        file.getChannel().transferFrom(readableByteChannel, 0 , Long.MAX_VALUE);
        System.out.println("OK");

//        "https://api.telegram.org/bot"+token+"/getFile?file_id="+file_id;

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Response response = okHttpClient.newCall(new Request.Builder()
//                .urlAboutFile(urlAboutFile)
//                .build()).execute();
//        String body = response.message();
//        return body;

//        HttpURLConnection urlConnection =(HttpURLConnection) urlAboutFile.openConnection();
//        String responseMessage = urlConnection.getResponseMessage();
//
//        return responseMessage;

    }

    private String getFileInfo(String file_id) {
        URL urlAboutFile = null;
        try {
            urlAboutFile = new URL("https://api.telegram.org/bot<token>/getFile?file_id="+file_id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlAboutFile.openStream()))) {
            String template = "";
            while ((template = br.readLine()) != null) {
                sb.append(template);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonParser(
                sb.toString());
    }

    private String jsonParser(String jsonString){
        JsonFactory factory = new JsonFactory();
        String text= "";
        try (JsonParser parser = factory.createParser(jsonString)) {
            while (!parser.isClosed()) {
                JsonToken jsonToken = parser.nextToken();
                if (jsonToken.equals(JsonToken.FIELD_NAME)) {
                    if (parser.getText().equals("file_path")) {
                        parser.nextToken();
                        text = parser.getValueAsString();
                        parser.close();
                    }
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return text;
    }
}
