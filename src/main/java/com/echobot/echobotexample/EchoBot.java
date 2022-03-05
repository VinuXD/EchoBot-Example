package com.echobot.echobotexample;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
class EchoBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN;
    private final String BOT_USERNAME;

    EchoBot(@Value("${bot.BOT_TOKEN}") String BOT_TOKEN, @Value("${bot.BOT_USERNAME}") String BOT_USERNAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_USERNAME = BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setReplyToMessageId(update.getMessage().getMessageId());
            message.setText(update.getMessage().getText());
            try {
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void start() {
        System.out.println(getBotUsername() + " started Successfully");
    }
}