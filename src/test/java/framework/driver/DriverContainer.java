package framework.driver;

import com.codeborne.selenide.WebDriverRunner;
import framework.utils.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Класс-контейнер, содержащий экземпляры веб-драйвера, готовые к использованию.
 */
public class DriverContainer {

    private final static ThreadLocal<HashMap<Instance, RemoteWebDriver>> drivers = new InheritableThreadLocal<HashMap<Instance, RemoteWebDriver>>() {
        @Override
        protected HashMap<Instance, RemoteWebDriver> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     * Инициализация основного экземпляра драйвера.
     */
    public static void setDrivers() throws MalformedURLException {
        createDriver(Instance.FIRST);
        switchToFirst();
    }

    /**
     * Создание драйвера и помещение его в контейнер.
     *
     * @param instanceKey ключ, относящийся к определенному экземпляру драйвера.
     */
    private static void createDriver(Instance instanceKey) throws MalformedURLException {
        IDriverFactory driverFactory = new DriverFactory();
        RemoteWebDriver driver = driverFactory.getDriver();
        Logger.getInstance().info("Browser size is " + driver.manage().window().getSize().toString());
        drivers.get().put(instanceKey, driver);
    }

    /**
     * Переключение на основной экземпляр драйвера.
     */
    public static void switchToFirst() throws MalformedURLException {
        switchDriver(Instance.FIRST);
    }

    /**
     * Переключение на второй экземпляр драйвера.
     */
    public static void switchToSecond() throws MalformedURLException {
        switchDriver(Instance.SECOND);
    }

    /**
     * Переключение на третий экземпляр драйвера.
     */
    public static void switchToThird() throws MalformedURLException {
        switchDriver(Instance.THIRD);
    }

    /**
     * Переключение на определенный экземпляр драйвера.
     * Если драйвер с заданным ключом отсутствует в контейнере, то происходит его создание.
     *
     * @param instanceKey ключ, относящийся к определенному экземпляру драйвера.
     */
    private static void switchDriver(Instance instanceKey) throws MalformedURLException {
        if (drivers.get().get(instanceKey) == null) {
            createDriver(instanceKey);
        }
        WebDriverRunner.setWebDriver(drivers.get().get(instanceKey));
    }

    /**
     * Уничтожение всех созданных экземпляров драйверов и очищение контейнера.
     */
    public static void quit() {
        for (Instance instanceKey : drivers.get().keySet()) {
            drivers.get().get(instanceKey).quit();
        }
        drivers.get().clear();
    }

    /**
     * Ключи, идентифицирующие экземпляры драйверов.
     */
    public enum Instance {
        FIRST,
        SECOND,
        THIRD
    }

    /**
     * Получаем текущий запущенный драйвер
     *
     * @return WebDriver возвращаем текущий драйвер
     */
    public static WebDriver getCurrentDriver() {
        return WebDriverRunner.getWebDriver();
    }


    /**
     * Получаем текущий URL
     */
    public static String getCurrentUrl() {
        return WebDriverRunner.getWebDriver().getCurrentUrl();
    }
}
