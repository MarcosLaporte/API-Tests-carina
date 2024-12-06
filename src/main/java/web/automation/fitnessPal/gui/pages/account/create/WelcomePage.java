package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

public class WelcomePage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//main/div[3]/button")
    private ExtendedWebElement continueBtn;

    public WelcomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Override
    public boolean isPageOpened() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.urlContains("/account/create/welcome"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public FirstNameInputPage openCreateAccount() {
        this.continueBtn.click(5);
        LOGGER.info("Navigating to Create Account...");

        return new FirstNameInputPage(getDriver());
    }
}