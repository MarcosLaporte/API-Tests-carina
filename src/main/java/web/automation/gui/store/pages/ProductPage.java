package web.automation.gui.store.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class ProductPage extends AbstractPage {
    @FindBy(xpath = "//body[@id=\"product\"]")
    private ExtendedWebElement bodyProductEl;

    public ProductPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(bodyProductEl);
    }

    @FindBy(xpath = "//*[@id=\"main\"]/div[1]/div[2]/h1")
    public ExtendedWebElement productName;

    @FindBy(xpath = "//*[@id=\"main\"]/div[1]/div[2]/div[1]/div[2]/div/span[1]")
    public ExtendedWebElement priceTag;

    @FindBy(xpath = ".//input[@id=\"quantity_wanted\"]")
    public ExtendedWebElement qtyInput;

    @FindBy(xpath = ".//button[contains(@class, \"add-to-cart\")]")
    public ExtendedWebElement addToCartBtn;
}
