package com.example.Joke.service;

import com.example.Joke.Config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Random;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    final BotConfig config;
    @Autowired
    private JokeService jokeService;

    public Bot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }





    private void sendTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            String joke = null;

            switch (messageText){
                case "/jokes":
                    joke = jokeService.getRandomJokeDB().getText();
                    sendTextMessage(chatId, joke);// Отправляем шутку пользователю
                    break;
                case "/help":
                    String helpMessage = "Вот список команд, которые я понимаю:\n"
                            + "/jokes - Получить случайную шутку\n"
                            + "/help - Получить помощь";
                    sendTextMessage(chatId, helpMessage);
                    break;

                default:
                    String defaultMessage = "Извините, я не понимаю эту команду.";
                    sendTextMessage(chatId, defaultMessage);
                    break;
            }

        }

    }

}
