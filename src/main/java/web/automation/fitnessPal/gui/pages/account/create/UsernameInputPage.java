package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.Utils;
import web.automation.fitnessPal.gui.pages.account.common.SingleInputPageBase;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SingleInputPageBase.class)
public class UsernameInputPage extends SingleInputPageBase {
    public UsernameInputPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/username");
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/username");
    }

    public LastStepPage fillFieldsAndContinue(String name) {
        AccountFieldValidator.validateUsername(name);
        Utils.clearWebField(this.inputEl);
        this.inputEl.type(name);
        this.clickNext();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(2500));
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("Create a username-helper-text")
            ));

            throw new IllegalArgumentException(errorMsg.getText());
        } catch (TimeoutException _) {
            Utils.LOGGER.debug("Error message didn't show up. Username is valid.");
        }

        return new LastStepPage(getDriver());
    }

    public LastStepPage continueDefaultName() {
        String inputValue = this.inputEl.getAttribute("value");

        Utils.LOGGER.info("Checking if username is in use.");
        if (inputValue == null || inputValue.isEmpty())
            throw new IllegalArgumentException("No default username was provided by page.");

        this.clickNext();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@role=\"alert\"]/div[2]")
            ));

            throw new RuntimeException("Registration error: " + errorMsg.getText());
        } catch (TimeoutException e) {
            Utils.LOGGER.debug("Sign up was successful.");
        }

        return new LastStepPage(getDriver());
    }
}
