package bot.biblecamp;

import bot.biblecamp.bd.SeminarCounter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BibleCampBot extends TelegramLongPollingBot {

    public static final String FIRST_SEMINAR_KEY = "firstSeminar";
    public static final String SECOND_SEMINAR_KEY = "secondSeminar";
    public static final String THIRD_SEMINAR_KEY = "thirdSeminar";
    public static final String FOURTH_SEMINAR_KEY = "fourthSeminar";
    public static final String FIFTH_SEMINAR_KEY = "fifthSeminar";


    public static final String FIRST_SEMINAR = "Кроссфит";
    public static final String SECOND_SEMINAR = "Что нам может сказать Джордан Питерсон?";
    public static final String THIRD_SEMINAR = "Как составить резюме и вести себя на собеседовании?";
    public static final String FOURTH_SEMINAR = "Уход за волосами и макияж: ошибки и мифы";
    public static final String FIFTH_SEMINAR = "Уход за волосами и макияж: ошибки и мифы";

    private static final List<InlineKeyboardButton> chatStart = Arrays.asList(
            InlineKeyboardButton.builder().text("Регистрация на семинар").callbackData("seminar").build(),
           // InlineKeyboardButton.builder().text("Молитва").callbackData("pray").build(),
            InlineKeyboardButton.builder().text("Результат").callbackData("result").build()
    );

    private static final List<InlineKeyboardButton> chatContinue = Arrays.asList(
            InlineKeyboardButton.builder().text("Отменить регистрацию").callbackData("delete").build(),
            InlineKeyboardButton.builder().text("Результат").callbackData("result").build()
    );

    private static final List<List<InlineKeyboardButton>> seminars = Arrays.asList(
            Collections.singletonList(InlineKeyboardButton.builder().text(FIRST_SEMINAR).callbackData(FIRST_SEMINAR_KEY).build()),
            Collections.singletonList(InlineKeyboardButton.builder().text(SECOND_SEMINAR).callbackData(SECOND_SEMINAR_KEY).build()),
            Collections.singletonList(InlineKeyboardButton.builder().text(THIRD_SEMINAR).callbackData(THIRD_SEMINAR_KEY).build()),
            Collections.singletonList(InlineKeyboardButton.builder().text(FOURTH_SEMINAR).callbackData(FOURTH_SEMINAR_KEY).build())
            //     Collections.singletonList(InlineKeyboardButton.builder().text(FIFTH_SEMINAR).callbackData("fifthSeminar").build())
    );


    @Override
    public String getBotUsername() {
        return "@biblecampbot";
    }

    @Override
    public String getBotToken() {
        return "5436924184:AAG3kzYpNhtkxYYouytl6R4lCpowKo0n8M4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            List<List<InlineKeyboardButton>> startButtons = new ArrayList<>();
            startButtons.add(chatStart);

            if (message.hasText()) {
                if ("понедельник".equalsIgnoreCase(message.getText())) {
                    try {
                        execute(SendMessage.builder().chatId(message.getChatId())
                                .text("Что вы хотите сделать? ")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(startButtons).build()).build());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if ("цвор".equalsIgnoreCase(message.getText())){
                    try {
                        SendPhoto photoMessage = new SendPhoto(message.getChatId().toString(), new InputFile(new File("D:\\camp\\bibleCamp2022\\cvor.jpg")));


                        execute(photoMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }

    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getFrom().getId();

        switch (callbackQuery.getData()) {
            case "result":
                try {
                    execute(SendMessage.builder()
                            .text("зарегистрировались на семинар: " + SeminarCounter.getResult()).chatId(chatId)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(chatContinue)).build())
                            .build());

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "seminar":
                try {
                    execute(SendMessage.builder()
                            .text("Выберите желаемый семинар:").chatId(chatId)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(seminars).build())
                            .build());

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case FIRST_SEMINAR_KEY:
                try {
                    boolean isAdd = SeminarCounter.addUserToSeminar(FIRST_SEMINAR_KEY, chatId);
                    String response = isAdd ? "Спасибо, вы зарегистрированы" : "вы уже были зарегистрированы";
                    execute(SendMessage.builder().chatId(chatId)
                            .text(response)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(chatContinue)).build())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case SECOND_SEMINAR_KEY:
                try {
                    boolean isAdd = SeminarCounter.addUserToSeminar(SECOND_SEMINAR_KEY, chatId);
                    String response = isAdd ? "Спасибо, вы зарегистрированы" : "вы уже были зарегистрированы";
                    execute(SendMessage.builder().chatId(chatId)
                            .text(response)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(chatContinue)).build())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case THIRD_SEMINAR_KEY:
                try {
                    boolean isAdd = SeminarCounter.addUserToSeminar(THIRD_SEMINAR_KEY, chatId);
                    String response = isAdd ? "Спасибо, вы зарегистрированы" : "вы уже были зарегистрированы";
                    execute(SendMessage.builder().chatId(chatId)
                            .text(response)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(chatContinue)).build())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case FOURTH_SEMINAR_KEY:
                try {
                    boolean isAdd = SeminarCounter.addUserToSeminar(FOURTH_SEMINAR_KEY, chatId);
                    String response = isAdd ? "Спасибо, вы зарегистрированы!" : "вы уже были зарегистрированы";
                    execute(SendMessage.builder().chatId(chatId)
                            .text(response)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(chatContinue)).build())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    boolean isDeleted = SeminarCounter.deleteUserChatId(chatId);
                    String response = isDeleted ? "Спасибо, вы отменили регистрацию" : "вы еще не были зарегистрированы";
                    execute(SendMessage.builder().chatId(chatId)
                            .text(response)
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(chatStart)).build())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

        }
    }


}
