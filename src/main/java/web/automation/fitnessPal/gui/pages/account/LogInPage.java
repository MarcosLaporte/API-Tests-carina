package web.automation.fitnessPal.gui.pages.account;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import web.automation.fitnessPal.Utils;
import web.automation.fitnessPal.gui.pages.SignedInHomePage;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class LogInPage extends AbstractPage {
    public LogInPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/account/login");
    }

    @FindBy(id = "email")
    ExtendedWebElement emailInput;

    @FindBy(id = "password")
    ExtendedWebElement passwordInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    ExtendedWebElement submitBtn;

    @FindBy(xpath = "//div[@role=\"alert\"]/div[2]")
    ExtendedWebElement errorMsg;

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().contains("/account/login");
    }

    public SignedInHomePage fillFieldsAndContinue(String email, String password) {
        this.emailInput.type(email);
        this.passwordInput.type(password);

        this.submitBtn.click();

        if (waitUntil(ExpectedConditions.visibilityOfElementLocated(errorMsg.getBy()), Duration.ofSeconds(5)))
            throw new IllegalArgumentException(errorMsg.getText());

        Utils.LOGGER.debug("Error message didn't show up. Fields are valid.");

        return new SignedInHomePage(getDriver());
    }
}
