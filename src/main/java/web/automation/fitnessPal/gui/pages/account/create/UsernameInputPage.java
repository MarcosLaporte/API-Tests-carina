package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.gui.pages.account.common.SingleInputPageBase;

import java.time.Duration;

import static web.automation.fitnessPal.Utils.LOGGER;

public class UsernameInputPage extends SingleInputPageBase {
    public UsernameInputPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/username");
    }

    public LastStepPage setNameInput(String name) {
        String inputValue = this.inputEl.getAttribute("value");
        if (inputValue != null && !inputValue.isEmpty()) {
            this.clickNext();
            return new LastStepPage(getDriver());
        }

        AccountFieldValidator.validateUsername(name);
        this.inputEl.sendKeys(Keys.valueOf(name));
        this.clickNext();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//form//p[@id=\"Create a username-helper-text\"]")
            ));

            throw new IllegalArgumentException(errorMsg.getText());
        } catch (TimeoutException _) {
            LOGGER.debug("Error message didn't show up. Username is valid.");
        }

        return new LastStepPage(getDriver());
    }
}
