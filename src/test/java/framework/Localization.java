package framework;

import framework.utils.JSONUtils;
import framework.utils.Logger;
import org.json.simple.JSONObject;

import static java.lang.String.format;

/**
 * Класс-синглтон, отвечающий за получение данных из ресурсных файлов конфигурации в зависимости от локали.
 */
public class Localization {

    private static Localization instance = null;

    private JSONObject localizedLabels;
    private Locale locale;

    private Localization() {
    }

    /**
     * Метод, возвращающий единственный экземпляр класса. Если экземпляр не создан - создает новый.
     *
     * @return экземпляр класса.
     */
    public static synchronized Localization getInstance() {
        if (instance == null) {
            instance = new Localization();
        }
        return instance;
    }

    /**
     * Устанавливает значение локали и считывает значения из labels.json.
     *
     * @param locale локаль, которую необходимо установить.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        String filePath = framework.Configuration.getInstance().getLocalizationFilePath(this.locale.getLocale(), "labels.json");
        localizedLabels = JSONUtils.readFromFile(filePath);
    }

    public String getLocale() {
        return locale.getLocale();
    }

    /**
     * Получение локализованного строкового значения лэйбла по ключу.
     *
     * @param token ключ, по которому необходимо искать значение.
     * @return локализованное значение лэйбла. Если значение не найдено, возвращается null.
     */
    public String getValue(String token) {
        String value = (String) localizedLabels.get(token);
        if (value != null) {
            return value;
        } else {
            Logger.getInstance().error(format("Token '%s' was not found for locale '%s'", token, locale.getLocale()));
            return null;
        }
    }

    /**
     * Перечисление, представляющее возможные значения локали.
     */
    public enum Locale {
        EN("en"),
        RU("ru");

        private String loc;

        Locale(String loc) {
            this.loc = loc;
        }

        /**
         * Получение строкового представления перечисления.
         *
         * @return строковое значение локали.
         */
        public String getLocale() {
            return this.loc;
        }
    }
}
