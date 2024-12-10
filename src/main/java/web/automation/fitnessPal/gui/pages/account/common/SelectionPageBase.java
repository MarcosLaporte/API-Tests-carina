package web.automation.fitnessPal.gui.pages.account.common;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.fitnessPal.objects.account.enums.Options;

import java.util.Set;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public abstract class SelectionPageBase<T extends Options, U extends AbstractPage> extends CreatePageBase {
    public SelectionPageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div[@role=\"group\"]")
    public ExtendedWebElement btnsContainer;

    @Context(dependsOn = "btnsContainer")
    @FindBy(xpath = ".//button[%d]")
    protected ExtendedWebElement buttonEl;

    public abstract U selectAndContinue(Set<T> options);

}