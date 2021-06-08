package framework.base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import framework.utils.Logger;


/**
 * Базовый класс, описывающий любой PageObject.
 */
public abstract class PageObject {

    protected static final Logger LOG = Logger.getInstance();

    private static final String BODY_LOC = "body";

    public SelenideElement shouldBe(Condition condition) {
        return getMainElement().shouldBe(condition);
    }

    public SelenideElement shouldNotBe(Condition condition) {
        return getMainElement().shouldNotBe(condition);
    }

    /**
     * Возвращает уникальный элемент, по которому можно идентифицировать PageObject.
     *
     * @return SelenideElement, найденный по заданному локатору.
     */
    protected abstract SelenideElement getMainElement();

}
