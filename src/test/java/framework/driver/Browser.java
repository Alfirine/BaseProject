package framework.driver;

/**
 * Перечисление, описывающее возможные браузеры для запуска тестов.
 */
public enum Browser {
    CHROME("chrome"), FIREFOX("firefox");

    private final String browser;

    Browser(String browser) {
        this.browser = browser;
    }

    /**
     * Получение строкового представления.
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Получение элемента перечисления по его строковому представлению.
     *
     * @param browser строковое представление элемента перечисления.
     * @return элемент перечисления. Если элемент не найден, либо передано не корректное значение,
     * возвращается дефолтное значени Browser.CHROME.
     */
    public static Browser getValue(String browser) {
        Browser result = Browser.CHROME;
        if (browser != null) {
            try {
                result = Browser.valueOf(browser.toUpperCase());
            } catch (IllegalArgumentException e) {
                return result;
            }
        }
        return result;
    }
}
