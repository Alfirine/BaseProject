package framework.base;


import com.codeborne.selenide.logevents.SelenideLogger;
import framework.Localization;
import framework.driver.DriverContainer;
import framework.utils.Logger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.util.Date;

import static java.lang.String.format;

/**
 * Базовый класс, описывающий любой тест.
 */
public abstract class BaseTest {

    protected static final Logger LOG = Logger.getInstance();

    /**
     * Метод, выполняющийся один раз перед всеми тестами в сьюте.
     * Устанавливает локализацию.
     */
    @BeforeSuite
    public void beforeSuite() {
        Localization.getInstance().setLocale(Localization.Locale.RU);
    }

    /**
     * Метод, выполняюшщийся перед каждый тестом (@Test) в сьюте.
     * Инициализирует веб-драйвер, добавляет слушатель AllureSelenide с целью логирования действий Selenide'а, логирует имя теста.
     * @param testContext текущий контекст теста.
     */
    @BeforeMethod
    public void beforeMethod(ITestContext testContext) throws MalformedURLException {
        DriverContainer.setDrivers();
        Date date = new Date();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        System.out.println("Дата и время запуска: " + date);
        LOG.info(format("Test '%s' started", testContext.getName()));
    }

    /**
     * Метод, выполняющийся после каждого теста (@Test) в сьюте.
     * Закрывает браузер, логирует имя выполнившегося теста.
     *
     * @param testContext текущий контекст теста.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext testContext) {
        DriverContainer.quit();
        LOG.info(format("Test '%s' finished", testContext.getName()));
    }
}
