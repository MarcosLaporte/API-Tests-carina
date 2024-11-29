package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class ListedProducts extends AbstractUIObject {
    public enum ProductList {
        POPULAR("Popular Products"),
        SALE("On sale"),
        NEW("New products");

        public final String displayText;

        ProductList(String displayText) {
            this.displayText = displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }

    @FindBy(xpath = ".//div[contains(@class, \"products\")]/div[contains(@class, \"js-product\")]/article")
    private List<WebElement> productElementList;

    private final List<ProductCard> productCardList;

    public ListedProducts(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new DefaultElementLocatorFactory(searchContext), this);

        productCardList = productElementList
                .stream()
                .map(el -> new ProductCard(driver, el))
                .toList();
    }

    public List<ProductCard> getProductCardList() {
        return this.productCardList;
    }

}
