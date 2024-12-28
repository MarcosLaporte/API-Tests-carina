package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class ProductInCart extends AbstractUIObject {
    public ProductInCart(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new DefaultElementLocatorFactory(searchContext), this);
    }

    @FindBy(xpath = ".//div[@class=\"product-line-info\"]/a")
    public WebElement productName;

    @FindBy(xpath = ".//span[@class=\"price\"]")
    public WebElement priceTag;

    @FindBy(xpath = ".//input[@type=\"number\"]")
    public WebElement productQtyInput;

    @FindBy(xpath = ".//span[@class=\"product-price\"]")
    public WebElement totalPrice;

    @FindBy(xpath = ".//a[@class=\"remove-from-cart\"]")
    public WebElement removeProductBtn;


}