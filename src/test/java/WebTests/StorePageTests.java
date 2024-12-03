package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import web.automation.gui.store.components.HeaderNav;
import web.automation.gui.store.components.ProductCard;
import web.automation.gui.store.pages.CartPage;
import web.automation.gui.store.pages.HomePage;
import web.automation.gui.store.pages.ProductPage;
import web.automation.objects.Cart;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;

public class StorePageTests implements IAbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final By HEADER_NAV_BY = By.xpath("//*[@id=\"header\"]/nav");

    @Test
    public void checkProductCard() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page was not opened.");

        List<ProductCard> productCardList = homePage.getFeaturedProducts(HomePage.FeaturedList.SALE);
        Assert.assertFalse(productCardList.isEmpty(), "Product List is empty.");

        int randProdIndex = productCardList.size() > 1 ? new Random().nextInt(0, productCardList.size() - 1) : 0;

        ProductCard selectedProd = productCardList.get(randProdIndex);
        selectedProd.scrollTo();
        String selectedProdName = selectedProd.productName.getText();
        String selectedProdPrice = selectedProd.priceTag.getText();
        LOGGER.info("Selected product in list: {}", selectedProd);

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

    @Test(invocationCount = 3)
    public void addToAndCheckCart() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page was not opened.");

        Random random = new Random();
        List<ProductCard> productCards = homePage.selectProducts(HomePage.FeaturedList.POPULAR, 3);

        Cart cart = new Cart();
        for (ProductCard productCard : productCards) {
            int qty = random.nextInt(1, 5);
            cart.addProduct(
                    productCard.addToCart(qty),
                    qty
            );
        }
        LOGGER.info("All products added to the cart!");

        HeaderNav headerNav = new HeaderNav(driver, driver.findElement(HEADER_NAV_BY));

        CartPage cartPage = headerNav.openCartPage();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page was not opened.");

        Assert.assertTrue(cartPage.cartBelongsInPage(cart));
    }

    @Test
    public void checkScrolling() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page was not opened.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Function<String, WebElement> getEl = path -> driver.findElement(By.xpath(path));

        LOGGER.info("Waiting for all the elements to load...");
        SortedMap<String, WebElement> elementMap = new TreeMap<>((Map.of(
                "1. Featured Sale list", getEl.apply(homePage.getFeaturedListPath.apply(HomePage.FeaturedList.SALE.displayText)),
                "2. Footer", homePage.footerEl,
                "3. Custom text block", homePage.customTextBlockEl,
                "4. Featured Popular list", getEl.apply(homePage.getFeaturedListPath.apply(HomePage.FeaturedList.POPULAR.displayText)),
                "5. Banner", homePage.bannerEl,
                "6. Carousel", homePage.carouselEl,
                "7. Featured New list", getEl.apply(homePage.getFeaturedListPath.apply(HomePage.FeaturedList.NEW.displayText))
        )));

        wait.until(ExpectedConditions.visibilityOfAllElements(elementMap.values().stream().toList()));

        Actions actions = new Actions(driver);
        for (Map.Entry<String, WebElement> entry : elementMap.entrySet()) {
            LOGGER.info(entry.getKey());
            WebElement element = entry.getValue();
            try {
                if (element instanceof ExtendedWebElement)
                    ((ExtendedWebElement) element).scrollTo();
                else
                    actions.scrollToElement(element).perform();
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}