package bot.biblecamp.bd;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static bot.biblecamp.BibleCampBot.*;

public class SeminarCounter {

    private static ConcurrentMap<String, Set<Long>> staticDataBase = new ConcurrentHashMap<>();

    static {
        staticDataBase.put(FIRST_SEMINAR_KEY, new HashSet<>());
        staticDataBase.put(SECOND_SEMINAR_KEY, new HashSet<>());
        staticDataBase.put(THIRD_SEMINAR_KEY, new HashSet<>());
        staticDataBase.put(FOURTH_SEMINAR_KEY, new HashSet<>());

    }

    public static boolean addUserToSeminar(String seminarName, Long chatId) {
        Set<Long> usersChatIds = SeminarCounter.staticDataBase.get(seminarName);
        boolean isValueContains = Boolean.FALSE;
        for(Map.Entry<String, Set<Long>> entry : staticDataBase.entrySet()){
            if(entry.getValue().contains(chatId)){
                isValueContains = Boolean.TRUE;
            }
        }
        if (!isValueContains){
            usersChatIds.add(chatId);
            return true;
        } else {
            return false;
        }
    }


    public static boolean deleteUserChatId(Long chatId) {
        String seminarName = getKey(chatId);
        if(Objects.nonNull(seminarName)){
            Set<Long> usersChatIdsFirst = SeminarCounter.staticDataBase.get(getKey(chatId));
            return usersChatIdsFirst.remove(chatId);
        }
        return false;
    }

    public static String getKey(Long value) {
        for (Map.Entry<String, Set<Long>> entry : staticDataBase.entrySet()) {
            if (entry.getValue().contains(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getResult() {
        return String.format(
                FIRST_SEMINAR + ": %s \r\n" +
                        SECOND_SEMINAR + ": %s \r\n" +
                        THIRD_SEMINAR + ": %s \r\n" +
                        FOURTH_SEMINAR + ": %s ",
                staticDataBase.get(FIRST_SEMINAR_KEY).size(),
                staticDataBase.get(SECOND_SEMINAR_KEY).size(),
                staticDataBase.get(THIRD_SEMINAR_KEY).size(),
                staticDataBase.get(FOURTH_SEMINAR_KEY).size()

        );

    }
}
