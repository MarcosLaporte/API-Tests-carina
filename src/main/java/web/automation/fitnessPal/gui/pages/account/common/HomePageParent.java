package web.automation.fitnessPal.gui.pages.account.common;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.fitnessPal.gui.pages.SignedInHomePage;
import web.automation.fitnessPal.gui.pages.SignedOffHomePage;

import java.time.Duration;

import static web.automation.fitnessPal.Utils.LOGGER;

public abstract class HomePageParent extends AbstractPage {
    protected HomePageParent(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @FindBy(xpath = "//*[@id=\"__next\"]//header")
    private ExtendedWebElement header;

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().equals("https://www.myfitnesspal.com/");
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

    @Override
    public void open() {
        this.getDriver().navigate().to("https://www.myfitnesspal.com/");

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement iframeEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("iframe#sp_message_iframe_1164399")
        ));
        driver.switchTo().frame(iframeEl);

        LOGGER.info("Accepting cookies...");
        WebElement acceptCookiesBtn = driver.findElement(By.xpath("//button[@title=\"OK\"]"));
        acceptCookiesBtn.click();
        driver.switchTo().defaultContent();
    }
}
