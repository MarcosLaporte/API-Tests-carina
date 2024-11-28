package web.automation.gui.store.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductPage.class)
public class ProductPage extends AbstractPage {

    public ProductPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @FindBy(xpath = "//*[@id=\"product_details\"]/div/div[2]/div/div/h1/span")
    public ExtendedWebElement productName;

    @FindBy(xpath = "//*[@id=\"product_details\"]/div/div[2]/div/div/div[1]/div/div")
    public ExtendedWebElement priceTag;

    @FindBy(xpath = "//*[@id=\"product_quantity\"]")
    public ExtendedWebElement productQtyInput;

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().contains("rt=product/product");
    }
}
