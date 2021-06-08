package framework.driver;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

/**
 * Интерфейс, описывающий фабрику для создания экземпляра веб-драйвера.
 */
public interface IDriverFactory {
    RemoteWebDriver getDriver() throws MalformedURLException;
}
