package tests;

import framework.base.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import steps.MainSteps;

public class NewTest extends BaseTest {
    MainSteps mainSteps = new MainSteps();

    @BeforeTest
    public void readParams() {
    }

    @Test
    protected void test() {
        mainSteps.mainStep();
    }
}
