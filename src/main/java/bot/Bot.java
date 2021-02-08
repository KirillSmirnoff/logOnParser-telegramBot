package bot;

import JSON.JsonHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "roboStatic_bot";
    }

    @Override
    public String getBotToken() {
        return "<token>";
    }

    @Override
    public void onUpdateReceived(Update update) {
        JsonHandler jsonHandler = new JsonHandler();
        BotMethods methods = new BotMethods();
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields

        if (update.hasMessage() && update.getMessage().hasText()) {
            message.setChatId(update.getMessage().getChatId().toString());
            try {
                String mesage = jsonHandler.convert(update.getMessage().getText());
                message.setText(mesage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasDocument()) { // Обработка документа
            message.setChatId(update.getMessage().getChatId().toString());
            try {
                methods.getFile(update);
                message.setText("Обработка файлов в бесплатной версии не поддерживается");

                execute(message);
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
