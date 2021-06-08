package framework.utils;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Класс-утилита для работы с файлами.
 */
public class FileManager {

    private FileManager() {
        throw new IllegalStateException("File manager class. All methods are static, don't create instance of this class");
    }

    /**
     * Получение абсолютного пути к файлу.
     *
     * @param path путь к файлу.
     * @return абсолютный путь к файлу.
     */
    public static String getAbsolutePath(Path path) {
        return getAbsolutePath(path.toString());
    }

    /**
     * Получение абсолютного пути к файлу. Поиск производится относительно рабочего директория, либо проектного.
     *
     * @param path путь к файлу.
     * @return абсолютный путь к файлу.
     */
    public static String getAbsolutePath(String path) {
        String userDir = System.getProperty("user.dir");
        String baseDir = userDir != null ? userDir : System.getProperty("project.basedir");
        return Paths.get(baseDir, path).toString();
    }
}
