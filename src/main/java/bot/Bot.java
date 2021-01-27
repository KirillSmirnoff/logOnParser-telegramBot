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
        return "1526625239:AAGUlrDQlu8FN_iXT1BpSu8FM8u8UitGtvk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        JsonHandler jsonHandler = new JsonHandler();

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
                    message.setChatId(update.getMessage().getChatId().toString());
            try {
                String mesage = jsonHandler.jsonHandlerToTelegram(update.getMessage().getText());
                message.setText(mesage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
