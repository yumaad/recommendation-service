package ru.bank.star.recommendation.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private final RecommendationService recommendationService;

    public TelegramBotService(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public String getBotUsername() {
        return "YourBotName";
    }

    @Override
    public String getBotToken() {
        return "YourTokenHere";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (text.startsWith("/recommend")) {
                String[] parts = text.split(" ");
                if (parts.length == 2) {
                    String username = parts[1];
                    String result = recommendationService.getRecommendations(username).toString();
                    sendMessage(chatId, result);
                } else {
                    sendMessage(chatId, "Используйте: /recommend username");
                }
            } else {
                sendMessage(chatId, "Добро пожаловать! Используйте команду: /recommend username");
            }
        }
    }

    private void sendMessage(String chatId, String text) {
        try {
            execute(new SendMessage(chatId, text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
