package web.automation.gui.fitnessPal.pages.account.common;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.SignedInHomePage;
import web.automation.gui.fitnessPal.pages.SignedOffHomePage;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public abstract class HomePageParent extends AbstractPage {
    protected HomePageParent(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://www.myfitnesspal.com/");
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @FindBy(css = "iframe#sp_message_iframe_1164399")
    private ExtendedWebElement cookiesIframe;

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().equals("https://www.myfitnesspal.com");
    }

    public HomePageParent getHomePage() {
        WebDriver driver = getDriver();
        try {
            driver.findElement(By.xpath("//*[@id=\"__next\"]//header"));
        } catch (NoSuchElementException e) {
            return new SignedOffHomePage(getDriver());
        }

        return new SignedInHomePage(getDriver());
    }

    public void acceptCookies() {
        WebDriver driver = getDriver();

        try {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(cookiesIframe.getBy()), Duration.ofSeconds(5));
        } catch (TimeoutException e) {
            Utils.LOGGER.error("Cookies iframe was not found.");
            return;
        }

        driver.switchTo().frame(cookiesIframe.getElement());

        Utils.LOGGER.info("Accepting cookies...");
        WebElement acceptCookiesBtn = driver.findElement(By.xpath("//button[@title=\"OK\"]"));
        acceptCookiesBtn.click();

        driver.switchTo().defaultContent();
    }
}
