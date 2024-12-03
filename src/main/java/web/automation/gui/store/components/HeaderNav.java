package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import com.zebrunner.carina.webdriver.locator.ExtendedElementLocatorFactory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.pages.CartPage;

import java.lang.invoke.MethodHandles;

public class HeaderNav extends AbstractUIObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HeaderNav(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new ExtendedElementLocatorFactory(driver, searchContext), this);
    }

    @FindBy(xpath = "//*[@id=\"_desktop_cart\"]//a")
    public WebElement cartBtn;

    public CartPage openCartPage() {
        this.cartBtn.click();
        LOGGER.info("Navigating to Cart page...");

        return new CartPage(getDriver());
    }
}