package web.automation.gui.store.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.components.ListedProducts;

import java.lang.invoke.MethodHandles;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class HomePage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//*[@id=\"carousel\"]")
    private ExtendedWebElement carouselEl;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(carouselEl);
    }

    @Override
    public void open() {
        getDriver().navigate().to("https://teststore.automationtesting.co.uk/");
    }

    public ListedProducts getProductsInList(ListedProducts.ProductList productList) {
        LOGGER.info("Getting listed products from {} list...", productList);

        String xPath = "//section[contains(@class, \"featured-products\")]//h2[normalize-space(text())=\"" + productList + "\"]/..";
        WebElement listSectionEl = getDriver().findElement(By.xpath(xPath));

        if (listSectionEl == null)
            LOGGER.warn("List element was not found.");

        return new ListedProducts(getDriver(), listSectionEl);
    }
}
