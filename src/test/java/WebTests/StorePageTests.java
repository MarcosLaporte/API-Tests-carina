package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import web.automation.gui.store.components.ListedProducts;
import web.automation.gui.store.components.ProductCard;
import web.automation.gui.store.pages.HomePage;
import web.automation.gui.store.pages.ProductPage;

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

        ListedProducts listedProducts = homePage.getProductsInList(ListedProducts.ProductList.BESTSELLERS);
        List<ProductCard> productCardList = listedProducts.getProductCardList();

        Assert.assertFalse(productCardList.isEmpty(), "Product List is empty.");

        int randProdIndex = productCardList.size() > 1 ? new Random().nextInt(0, productCardList.size() - 1) : 0;

        ProductCard selectedProd = productCardList.get(randProdIndex);
        selectedProd.scrollTo();
        String selectedProdName = selectedProd.productName.getText();
        String selectedProdPrice = selectedProd.priceTag.getText();
        LOGGER.info("Selected product in list info: {}", selectedProd);

        ProductPage productPage = selectedProd.openProductPage();
        Assert.assertTrue(productPage.isPageOpened(), "Product page was not opened.");

        LOGGER.info("Product in page info: {}", selectedProd);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(selectedProdName.equalsIgnoreCase(productPage.productName.getText()), "Product name in page differs from list.");
        softAssert.assertTrue(selectedProdPrice.equalsIgnoreCase(productPage.priceTag.getText()), "Price tag in page differs from list.");

        softAssert.assertAll();
    }
}
