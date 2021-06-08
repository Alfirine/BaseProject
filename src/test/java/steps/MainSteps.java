package steps;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import project.objects.pages.MainPage;

import static com.codeborne.selenide.Condition.visible;

public class MainSteps {
    private MainPage mainPage;

    public MainSteps() {
        mainPage = new MainPage();
    }

        @Step("Check that page is open")
        public void mainStep() {
            WebDriverRunner.getWebDriver().navigate().to("https:ya.ru");
            mainPage.shouldBe(visible);
        }
}
