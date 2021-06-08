package framework.utils;

import io.qameta.allure.Step;

/**
 * Класс-утилита для логирования.
 */
public final class Logger {

    private static final org.apache.log4j.Logger LOG4J = org.apache.log4j.Logger.getLogger(Logger.class);
    private static Logger instance = null;

    private Logger() {
    }

    /**
     * Метод, возвращающий единственный экземпляр класса. Если экземпляр не создан - создает новый.
     *
     * @return экземпляр класса.
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Логирование инфомарции с тегом [DEBUG]
     *
     * @param message сообщение для логирования.
     */
    @Step("{0}")
    public void debug(final String message) {
        LOG4J.debug(message);
    }

    /**
     * Логирование инфомарции с тегом [INFO]
     *
     * @param message сообщение для логирования.
     */
    @Step("{0}")
    public void info(final String message) {
        LOG4J.info(message);
    }

    /**
     * Логирование инфомарции с тегом [WARN]
     *
     * @param message сообщение для логирования.
     */
    @Step("{0}")
    public void warn(final String message) {
        LOG4J.warn(message);
    }

    /**
     * Логирование инфомарции с тегом [ERROR]
     *
     * @param message сообщение для логирования.
     */
    @Step("{0}")
    public void error(final String message) {
        LOG4J.error(message);
    }

    /**
     * Логирование инфомарции с тегом [FATAL].
     *
     * @param message   сообщение для логирования.
     * @param throwable исключение, вызвавшее прерывание.
     */
    @Step("{0}")
    public void fatal(final String message, Throwable throwable) {
        LOG4J.fatal(message + ": " + throwable.toString());
    }
}

