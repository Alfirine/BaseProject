package framework.driver;

import com.codeborne.selenide.WebDriverRunner;
import framework.Configuration;
import framework.utils.FileManager;
import framework.utils.JSONUtils;
import framework.utils.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

import static java.lang.String.format;

/**
 * Класс (фабрика), отвечающий за создание экземпляра веб-драйвера.
 */
public class DriverFactory implements IDriverFactory {

    private static final Logger LOG = Logger.getInstance();
    private static final Browser BROWSER = Browser.getValue(Configuration.getInstance().getProperty("browser"));
    public static final Long ELEMENT_TIMEOUT = Configuration.getInstance().getTimeout("timeout.element.wait");
    private static final boolean REMOTE_WD = Boolean.parseBoolean(Configuration.getInstance().getProperty("remote.wd"));
    private static final String REMOTE_WD_URL = Configuration.getInstance().getProperty("browser.remote_wd.url");
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

    /**
     * Получение экземпляра веб-драйвера, готового к использованию.
     *
     * @return экземпляр локального или удаленного драйвера.
     */
    @Override
    public RemoteWebDriver getDriver() {
        RemoteWebDriver driver;
        if (REMOTE_WD) {
            driver = getRemoteDriver();
        } else {
            driver = getLocalDriver();
        }
        com.codeborne.selenide.Configuration.timeout = ELEMENT_TIMEOUT;
        com.codeborne.selenide.Configuration.pageLoadStrategy = "normal";

        return driver;
    }

    /**
     * Создание локального веб-драйвера.
     *
     * @return экземпляр локального драйвера.
     */
    private RemoteWebDriver getLocalDriver() {
        RemoteWebDriver driver;
        switch (BROWSER) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = getChromeOptions();
                chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = getFirefoxOptions();
                firefoxOptions.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException(format("Browser %s is not supported", BROWSER));
        }
        return driver;
    }

    /**
     * Создание удаленного веб-драйвера.
     *
     * @return экземпляр локального драйвера.
     */
    private RemoteWebDriver getRemoteDriver() {
        RemoteWebDriver driver;
        switch (BROWSER) {
            case CHROME:
                ChromeOptions chromeOptions = getChromeOptions();
                chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                chromeOptions.setCapability("enableVNC", true);
                driver = getRemoteWebDriver(chromeOptions);
                driver.setFileDetector(new LocalFileDetector());
                break;

            case FIREFOX:
                FirefoxOptions firefoxOptions = getFirefoxOptions();
                firefoxOptions.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                firefoxOptions.setCapability("enableVNC", true);
                driver = getRemoteWebDriver(firefoxOptions);
                driver.setFileDetector(new LocalFileDetector());
                break;
            default:
                throw new IllegalArgumentException(format("Browser %s is not supported", BROWSER));
        }
        return driver;
    }

    /**
     * Создание удаленного драйвера.
     *
     * @param caps Capabilities, описывающие драйвер, экземпляр которого необходимо создать.
     * @return экземпляр драйвера либо null, в случае некорректного URL.
     */
    private RemoteWebDriver getRemoteWebDriver(MutableCapabilities caps) {
        try {
            WebDriver driver = new RemoteWebDriver(
                    URI.create(REMOTE_WD_URL).toURL(), caps);
            WebDriverRunner.setWebDriver(driver);
            return (RemoteWebDriver) WebDriverRunner.getWebDriver();
        } catch (MalformedURLException e) {
            LOG.fatal("Unable to create Remote WebDriver", e);
            return null;
        }
    }

    /**
     * Создание экземпляра ChromeOptions, на основании файла driverOptions.json из ресурсов.
     *
     * @return ChromeOptions для инициализации драйвера.
     */
    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        String fileWithOptions = FileManager.getAbsolutePath(Configuration.getInstance().getProperty("browser.driver_options.file"));
        JSONObject options = (JSONObject) JSONUtils.readFromFile(fileWithOptions).get(Browser.CHROME.getBrowser());
        options = (JSONObject) options.get(getPlatformName());

        String[] args = JSONUtils.getStringArray(options, "args");

        chromeOptions.addArguments(args);

        JSONObject experimentalOptions = (JSONObject) options.get("experimentalOptions");
        String[] excludeSwitches = JSONUtils.getStringArray(experimentalOptions, "excludeSwitches");
        chromeOptions.setExperimentalOption("excludeSwitches", excludeSwitches);

        return chromeOptions;
    }

    /**
     * Создание экземпляра FirefoxOptions, на основании файла driverOptions.json из ресурсов.
     *
     * @return ChromeOptions для инициализации драйвера.
     */
    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        String fileWithOptions = FileManager.getAbsolutePath(Configuration.getInstance().getProperty("browser.driver_options.file"));
        JSONObject options = (JSONObject) JSONUtils.readFromFile(fileWithOptions).get(Browser.FIREFOX.getBrowser());
        options = (JSONObject) options.get(getPlatformName());

        String[] args = JSONUtils.getStringArray(options, "args");
        firefoxOptions.addArguments(args);

        return firefoxOptions;
    }

    /**
     * Получение имени платформы, на которой выполняются тесты.
     *
     * @return имя платформы.
     */
    private String getPlatformName() {
        String platform;
        if (PLATFORM.contains("win")) {
            platform = "windows";
        } else if (PLATFORM.contains("linux")) {
            platform = "linux";
        } else {
            throw new IllegalArgumentException(format("Platform %s is not supported", PLATFORM));
        }
        return platform;
    }
}
