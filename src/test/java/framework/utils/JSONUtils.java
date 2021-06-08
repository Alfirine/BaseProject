package framework.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.format;

/**
 * Класс-утилита для работы с JSON файлами.
 */
public class JSONUtils {

    private static final Logger LOG = Logger.getInstance();

    /**
     * Получение массива строк из объекта JSON по ключу.
     *
     * @param jsonObject экземпляр JSONObject.
     * @param key        ключ для полиска массива строк в объекте JSON.
     * @return массив строк, извлеченный из JSON объекта.
     */
    public static String[] getStringArray(JSONObject jsonObject, String key) {
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return new Gson().fromJson(jsonArray.toJSONString(), new TypeToken<String[]>() {
        }.getType());
    }

    /**
     * Чтение JSON файла в объект JSONObject.
     *
     * @param filePath путь к файлу.
     * @return экземпляр JSONObject.
     */
    public static JSONObject readFromFile(String filePath) {
        JSONObject data = null;
        try {
            data = (JSONObject) new JSONParser().parse(new FileReader(filePath));
        } catch (IOException | ParseException e) {
            LOG.fatal(format("Exception occurred during read data from file: %s", filePath), e);
        }
        return data;
    }
}
