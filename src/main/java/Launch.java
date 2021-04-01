import JSON.JsonHandler;
import bot.Bot;
import init.Init;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Launch {

    public static void main(String[] args) {
        Init.start();

        /*Регистрация телеграмм бота*/

//        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(System.getenv("PORT")))) {
//            Socket clientSocket = new Socket();
//            while (!clientSocket.isBound()) {
//                clientSocket = serverSocket.accept();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}