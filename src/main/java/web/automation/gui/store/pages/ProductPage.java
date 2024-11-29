package web.automation.gui.store.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductPage.class)
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

    @FindBy(xpath = "//*[@id=\"quantity_wanted\"]")
    public ExtendedWebElement productQtyInput;

    @FindBy(xpath = "//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")
    public ExtendedWebElement addToCartBtn;
}
