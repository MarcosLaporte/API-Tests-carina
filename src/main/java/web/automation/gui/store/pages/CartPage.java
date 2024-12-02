package web.automation.gui.store.pages;


import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.components.ProductInCart;
import web.automation.objects.Cart;
import web.automation.objects.Product;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class CartPage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//li[@class=\"cart-item\"]/div")
    private List<ExtendedWebElement> cartEl;

    public CartPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Override
    public void open() {
        getDriver().navigate().to(getDriver().getCurrentUrl() + "?controller=cart&action=show");
    }

    @FindBy(xpath = "//div[@id=\"cart-subtotal-products\"]/span[@class=\"value\"]")
    public ExtendedWebElement itemsTotalValue;

    @FindBy(xpath = "//div[contains(@class, \"checkout\")]//a")
    public ExtendedWebElement proceedBtn;

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().contains("controller=cart");
    }

    public List<ProductInCart> getProductsInCart() {
        return this.cartEl.stream()
                .map(el -> new ProductInCart(getDriver(), el))
                .toList();
    }

    public boolean cartBelongsInPage(Cart cart) {
        LOGGER.info("---------CHECKING CART---------");
        String totalInLocalCart = cart.getFormattedTotalAmount();
        String x = this.itemsTotalValue.getText();
        if (!totalInLocalCart.equals(x)){
            LOGGER.warn("Total prices don't match.");
            return false;
        }

        for (ProductInCart productEl : getProductsInCart()) {
            String prodName = productEl.productName.getText();
            Optional<Product> respectiveProd = cart.getProductByName(prodName);
            LOGGER.info("-------------------------------");

            if (respectiveProd.isEmpty()) {
                LOGGER.warn("'{}' does not match to any in the cart", prodName);
                return false;
            }

            LOGGER.debug(prodName);

            Actions actions = new Actions(getDriver());
            actions.scrollToElement(productEl.priceTag).perform();
            actions.moveToElement(productEl.productName).perform();

            Product product = respectiveProd.get();

            boolean hasSamePrice = (productEl.priceTag.getText()).equals(product.getPriceTag());

            LOGGER.debug("Price in cart conforms to product: {}", hasSamePrice);

            int formattedQty = cart.products.get(product);
            int inputValue = Integer.parseInt(productEl.productQtyInput.getAttribute("value"));
            boolean hasSameQty = inputValue == formattedQty;
            LOGGER.debug("Quantity in cart conforms to product: {}", hasSameQty);

            if (!hasSamePrice || !hasSameQty)
                return false;
        }

        return true;
    }
}