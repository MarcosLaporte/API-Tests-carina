package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import web.automation.gui.store.components.HeaderNav;
import web.automation.gui.store.components.ListedProducts;
import web.automation.gui.store.components.ProductCard;
import web.automation.gui.store.pages.CartPage;
import web.automation.gui.store.pages.HomePage;
import web.automation.gui.store.pages.ProductPage;
import web.automation.objects.Cart;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Random;

public class StorePageTests implements IAbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void checkProductCard() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page was not opened.");
        List<ProductCard> productCardList = homePage.getProductsInList(ListedProducts.ProductList.SALE);

        Assert.assertFalse(productCardList.isEmpty(), "Product List is empty.");

        int randProdIndex = productCardList.size() > 1 ? new Random().nextInt(0, productCardList.size() - 1) : 0;

        ProductCard selectedProd = productCardList.get(randProdIndex);
        selectedProd.scrollTo();
        String selectedProdName = selectedProd.productName.getText();
        String selectedProdPrice = selectedProd.priceTag.getText();
        LOGGER.info("Selected product in list info: {}", selectedProd);

        ProductPage productPage = selectedProd.openProductPage();
        Assert.assertTrue(productPage.isPageOpened(), "Product page was not opened.");

        LOGGER.info("Product in page info: {} - {}", selectedProdName, selectedProdPrice);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(
                selectedProdName.equalsIgnoreCase(productPage.productName.getText()),
                "Product name in page differs from list."
        );
        softAssert.assertTrue(
                selectedProdPrice.equalsIgnoreCase(productPage.priceTag.getText()),
                "Price tag in page differs from list."
        );

        softAssert.assertAll();
    }


    static By headerNavBy = By.xpath("//*[@id=\"header\"]/nav");

//    @Test(invocationCount = 5)
    @Test
    public void addToAndCheckCart() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page was not opened.");

        Random random = new Random();
        List<ProductCard> productCards = homePage.selectProducts(ListedProducts.ProductList.POPULAR, 3);

        Cart cart = new Cart();
        for (ProductCard productCard : productCards) {
            int qty = random.nextInt(1, 5);
            cart.addProduct(
                    productCard.addToCart(qty),
                    qty
            );
        }
        LOGGER.info("All products added to the cart!");

        HeaderNav headerNav = new HeaderNav(driver, driver.findElement(headerNavBy));

        CartPage cartPage = headerNav.openCartPage();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page was not opened.");

        Assert.assertTrue(cartPage.cartBelongsInPage(cart));
    }
}
