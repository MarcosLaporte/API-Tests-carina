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
        FEATURED("featured", "featured_1769"),
        LATEST("latest", "latest_1770"),
        BESTSELLERS("bestseller", "bestsellers_1771"),
        SPECIALS("special", "special_1772");

        public final String sectionId;
        public final String prodListDivId;

        ProductList(String sectionId, String prodListDivId) {
            this.sectionId = sectionId;
            this.prodListDivId = prodListDivId;
        }

        public String getListElementId() {
            return "block_frame_" + this.prodListDivId;
        }
    }

    @FindBy(xpath = ".//h1/span[@class=\"maintext\"]")
    public WebElement listName;

    @FindBy(xpath = ".//div[contains(@class, \"thumbnails\")]/div")
    private List<WebElement> productElementList;

    private final List<ProductCard> productCardList;

    public ListedProducts(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new DefaultElementLocatorFactory(searchContext), this);

        productCardList = productElementList
                .stream()
                .map(el -> new ProductCard(driver, el)).toList();
    }

    public List<ProductCard> getProductCardList() {
        return this.productCardList;
    }

}
