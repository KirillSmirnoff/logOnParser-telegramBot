package bot;

import JSON.JsonHandler;
import dao.OperationWithFiles;
import init.Init;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class Bot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "roboStatic_bot";
    }

    @Override
    public String getBotToken() {
        return Init.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        JsonHandler jsonHandler = new JsonHandler();
        OperationWithFiles operation = new OperationWithFiles();
        BotMethods methods = new BotMethods();
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        SendDocument document = new SendDocument();
        InputFile inputFile = new InputFile();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatID = update.getMessage().getChatId().toString();
            int length = update.getMessage().getText().length();
            try {
                if (length > 4050) {
                    message.setChatId(chatID);
                    message.setText("Лимит на ограничение длинны сообщений, информацию следует отправлять в текстовом файле");
                } else {
                    String messageString = jsonHandler.convert(update.getMessage().getText());
                    if(messageString.length() < 4050) {
                        message.setChatId(chatID);
                        message.setText(messageString);
                        execute(message);
                    }else {
                        File jsonAnswer = operation.saveResultToFile(messageString);
                        document.setChatId(chatID);
                        document.setDocument(inputFile.setMedia(jsonAnswer));
                        execute(document);
                    }
                }

            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasDocument()) { // Обработка документа
            document.setChatId(update.getMessage().getChatId().toString());
            try {
                document.setDocument(inputFile.setMedia(methods.convertFromFile(update)));
                execute(document);
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
