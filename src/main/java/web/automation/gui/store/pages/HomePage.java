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

    @FindBy(xpath = "//*[@id=\"maincontainer\"]/div/div/div[1]/section/div/h4")
    public ExtendedWebElement greetingsMsg;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(greetingsMsg);
    }

    @Override
    public void open() {
        getDriver().navigate().to("https://automationteststore.com/");
    }

    public ListedProducts getProductsInList(ListedProducts.ProductList productList) {
        LOGGER.info("Getting listed products from {} list...", productList);

        String xmlPath = "//*[@id=\"" + productList.getListElementId() + "\"]";
        WebElement listSectionEl = getDriver().findElement(By.xpath(xmlPath));
        if (listSectionEl == null)
            LOGGER.warn("List element was not found.");

        return new ListedProducts(getDriver(), listSectionEl);
    }
}
