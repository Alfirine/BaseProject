package framework;

import framework.utils.FileManager;
import framework.utils.PropertiesResourceManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для работы с различными конфигурациями окружений.
 */
public class Configuration {

    private static final String CONFIG_FILE_NAME = "configuration.properties";
    private static String envName;

    private static ThreadLocal<Configuration> instance = new InheritableThreadLocal<>();

    /**
     * Конструктор по умолчанию, получающий значение текущего окружения из основного конфигурационного файла.
     */
    private Configuration() {

        PropertiesResourceManager commonConfig = new PropertiesResourceManager(CONFIG_FILE_NAME);
        envName = commonConfig.getProperty("environment");
    }

    /**
     * Метод, возвращающий единственный потоконезависимый экземпляр класса. Если экземпляр не создан - создает новый.
     *
     * @return экземпляр класса.
     */
    public static Configuration getInstance() {
        if (instance.get() == null) {
            instance.set(new Configuration());
        }
        return instance.get();
    }

    /**
     * Получение конфигурационного значения по ключу.
     *
     * @param key ключ для поиска конфигурационного значения.
     * @return конфигурационное значение.
     */
    public String getProperty(final String key) {
        return getConfig().getProperty(key);
    }

    /**
     * Получение значение ожидания - строковое значение приведенное к типу Long.
     *
     * @param timeoutKey ключ для поиска значения в конфигурации.
     * @return конфигурационное значение.
     */
    public Long getTimeout(final String timeoutKey) {
        return Long.valueOf(getConfig().getProperty(timeoutKey));
    }

    /**
     * Получение набора пар ключ-значение из выбранной конфигурации.
     *
     * @return набор пар ключ-значение.
     */
    private PropertiesResourceManager getConfig() {
        Path envConfigPath = Paths.get(getEnvPath(), CONFIG_FILE_NAME);
        return new PropertiesResourceManager(CONFIG_FILE_NAME, String.valueOf(envConfigPath));
    }

    /**
     * Получение пути к локализационному файлу выбранного окружения.
     *
     * @param locale   локаль, для которой необходимо получить значения.
     * @param fileName имя файла, содержащего локализационные значения.
     * @return путь к файлу.
     */
    public String getLocalizationFilePath(String locale, String fileName) {
        String envPath = getEnvPath();
        return String.valueOf(FileManager.getAbsolutePath(Paths.get("src","test", "resources", envPath, "localization", locale, fileName)));
    }

    /**
     * Получение путь к выбранной конфигурации.
     *
     * @return путь с директорию с конфигурационным файлам и другими ресурсами.
     */
    private String getEnvPath() {
        return String.valueOf(Paths.get("environment", envName));
    }

}
