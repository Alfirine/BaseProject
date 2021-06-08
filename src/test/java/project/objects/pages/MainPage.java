package project.objects.pages;

import com.codeborne.selenide.SelenideElement;
import framework.base.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage {
    private static final String MAIN_ELEMENT_LOC = ".search2__input";

    @Override
    protected SelenideElement getMainElement() {
        return $(MAIN_ELEMENT_LOC);
    }


}
