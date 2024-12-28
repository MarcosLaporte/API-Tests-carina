package web.automation.gui.fitnessPal.pages.account.common;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public abstract class SingleInputPageBase extends CreatePageBase {
    public SingleInputPageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//input")
    public ExtendedWebElement inputEl;

}
