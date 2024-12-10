package web.automation.fitnessPal.gui.pages.account.common;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public abstract class CreatePageBase extends AbstractPage {
    protected final static String BASE_URL = "/account/create";

    public CreatePageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Override
    protected void setPageURL(String relURL) {
        super.setPageURL(BASE_URL + relURL);
    }

    @FindBy(xpath = "//section/div/main/div/form")
    public ExtendedWebElement formElement;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = "//nav/a")
    private ExtendedWebElement formBackBtn;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = "//nav//button")
    private ExtendedWebElement formNextBtn;

    public void clickBack() {
        this.formBackBtn.click(3);
    }

    public void clickNext() {
        this.formNextBtn.click(3);
    }

    @Override
    public boolean isPageOpened() {
        return this.getCurrentUrl().contains("/account/create");
    }
}
