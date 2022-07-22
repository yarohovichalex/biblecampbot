package bot.biblecamp;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BibleCampBot extends TelegramLongPollingBot {

    private static final List<InlineKeyboardButton> firstLine = Arrays.asList(
            InlineKeyboardButton.builder().text("Seminar").callbackData("orig").build(),
            InlineKeyboardButton.builder().text("Pray").callbackData("targ").build()
    );
    private static final List<InlineKeyboardButton> secondLine = Arrays.asList(
            InlineKeyboardButton.builder().text("Seminar").callbackData("orig").build(),
            InlineKeyboardButton.builder().text("Pray").callbackData("targ").build()
    );

    @Override
    public String getBotUsername() {
        return "@biblecampbot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            Message message = update.getMessage();
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(firstLine);
            buttons.add(secondLine);

            if(message.hasText()){
                try {
                    execute(SendMessage.builder().chatId(message.getChatId())
                            .text("Please select which operation do you want to do")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build()).build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
