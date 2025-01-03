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
import web.automation.gui.store.objects.Cart;
import web.automation.gui.store.objects.Product;

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
        try {
            String totalInLocalCart = cart.getFormattedTotalAmount();
            if (!totalInLocalCart.equals(this.itemsTotalValue.getText()))
                throw new ProductCartDifferException("Total prices don't match.");

            for (ProductInCart productEl : getProductsInCart()) {
                String prodName = productEl.productName.getText();
                Optional<Product> respectiveProd = cart.getProductByName(prodName);
                LOGGER.info("-------------------------------");

                if (respectiveProd.isEmpty())
                    throw new ProductCartDifferException(String.format("'%s' does not match to any in the cart", prodName));

                LOGGER.info("----- {} -----", prodName);

                Actions actions = new Actions(getDriver());
                actions.scrollToElement(productEl.priceTag).perform();
                actions.moveToElement(productEl.productName).perform();

                Product product = respectiveProd.get();

                String priceInPage = productEl.priceTag.getText();
                String priceLocalProd = product.getPriceTag();
                if (!priceInPage.equals(priceLocalProd))
                    throw new ProductCartDifferException("Prices don't match.");

                int qtyInPage = Integer.parseInt(productEl.productQtyInput.getAttribute("value"));
                int qtyLocalProd = cart.products.get(product);
                if (qtyInPage != qtyLocalProd)
                    throw new ProductCartDifferException("Quantities don't match.");
            }
        } catch (ProductCartDifferException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    private static class ProductCartDifferException extends Exception {
        public ProductCartDifferException(String message) {
            super(message);
        }
    }
}