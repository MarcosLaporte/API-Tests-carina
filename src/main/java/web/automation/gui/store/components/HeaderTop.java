package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import com.zebrunner.carina.webdriver.locator.ExtendedElementLocatorFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.pages.ProductCategoryPage;

import java.lang.invoke.MethodHandles;

public class HeaderTop extends AbstractUIObject {
    public enum Categories {
        CLOTHES("category-3"),
        ACCESSORIES("category-6"),
        ART("category-9");

        public final String elementId;

        Categories(String elementId) {
            this.elementId = elementId;
        }
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HeaderTop(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new ExtendedElementLocatorFactory(driver, searchContext), this);
    }

    @FindBy(xpath = ".//*[@id=\"_desktop_logo\"]//a")
    public WebElement logoBtn;

    @FindBy(xpath = ".//*[@id=\"search_widget\"]//input[@name=\"s\"]")
    public WebElement searchInput;

    public ProductCategoryPage openCategoryPage(Categories category) {
        WebElement categoryBtn = driver.findElement(By.id(category.elementId));
        categoryBtn.click();
        LOGGER.info("Navigating to {} category page...", category);

        return new ProductCategoryPage(getDriver());
    }
}
