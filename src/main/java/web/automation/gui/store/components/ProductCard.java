package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.pages.ProductPage;

import java.lang.invoke.MethodHandles;

public class ProductCard extends AbstractUIObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = ".//h3/a")
    public WebElement productName;

    @FindBy(xpath = ".//span[@class=\"price\"]")
    public WebElement priceTag;

    public ProductCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new DefaultElementLocatorFactory(searchContext), this);
    }

    public ProductPage openProductPage() {
        productName.click();
        LOGGER.info("Navigating to Product page...");
//        return initPage(driver, ProductPage.class);
        return new ProductPage(getDriver());
    }

    @Override
    public String toString() {
        return productName.getText() + " - " + priceTag.getText();
    }
}
