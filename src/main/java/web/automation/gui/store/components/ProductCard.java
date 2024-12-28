package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import com.zebrunner.carina.webdriver.locator.ExtendedElementLocatorFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.objects.Product;
import web.automation.gui.store.pages.ProductPage;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.function.Supplier;

public class ProductCard extends AbstractUIObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JavascriptExecutor JS_EXEC;

    public ProductCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        PageFactory.initElements(new DefaultElementLocatorFactory(searchContext), this);
        this.JS_EXEC = (JavascriptExecutor) driver;
    }

    @FindBy(xpath = ".//*[@class=\"h3 product-title\"]/a")
    private WebElement productName;

    @FindBy(xpath = ".//span[@class=\"price\"]")
    private WebElement priceTag;

    @FindBy(xpath = ".//a[@data-link-action=\"quickview\"]")
    public WebElement previewBtn;

    public ProductPage openProductPage() {
        this.productName.click();
        LOGGER.info("Navigating to Product page...");

        return new ProductPage(getDriver());
    }

    public String getProductName() {
        return this.productName.getText();
    }

    public String getProductPriceTag() {
        return this.priceTag.getText();
    }

    @Override
    public String toString() {
        return getProductName() + " - " + getProductPriceTag();
    }

    private ProductPreview openProductPreview() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Actions actions = new Actions(driver);

//        LOGGER.debug("Scrolling to {}...", this.getProductName());
        actions.scrollToElement(this.priceTag).perform();
//        LOGGER.debug("Hovering over name...");
        actions.moveToElement(this.productName).perform();
//        LOGGER.debug("Hovering over quick view...");
        actions.moveToElement(this.previewBtn).perform();

//        LOGGER.debug("Clicking...");
        wait.until(
                ExpectedConditions.elementToBeClickable(this.previewBtn)
        ).click();

        By previewModalBy = By.xpath("//*[contains(@id, \"quickview-modal\")]//div[@class=\"modal-content\"]");
        try {
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(previewModalBy)
                            .andThen(el -> new ProductPreview(getDriver(), el))
            );
        } catch (TimeoutException _) {
            throw new RuntimeException("Modal didn't show. Product preview was not shown.");
        }
    }

    public Product addToCart(int quantity) {
        if (quantity < 1)
            throw new RuntimeException("Product quantity cannot be less than 1.");

        LOGGER.info("Browsing page for '{}'...", this.getProductName());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Actions actions = new Actions(driver);

        ProductPreview preview = this.openProductPreview();

        LOGGER.info("Setting quantity ({})...", quantity);

        Supplier<String> getInputValue = () ->
                JS_EXEC.executeScript("return document.getElementById('quantity_wanted').value;").toString();
        do {
            wait.until(
                    ExpectedConditions.elementToBeClickable(preview.qtyInput)
            ).clear();

            actions.sendKeys(preview.qtyInput, Integer.toString(quantity)).perform();
        } while (!(getInputValue.get().equals(Integer.toString(quantity))));

        LOGGER.info("Adding to the cart...");
        wait.until(
                ExpectedConditions.elementToBeClickable(preview.addToCartBtn)
        ).click();

        Product product = new Product(preview.productName.getText(), preview.priceTag.getText());

        ProductAddedModal productAddedModal;
        try {
            productAddedModal = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("blockcart-modal"))
                            .andThen(el -> new ProductAddedModal(getDriver(), el))
            );
        } catch (TimeoutException _) {
            throw new RuntimeException("Modal didn't show. Product was not added to cart.");
        }

        LOGGER.info("{} '{}' added to cart!", quantity, this.getProductName());
        wait.until(
                ExpectedConditions.elementToBeClickable(productAddedModal.closeBtn)
        ).click();

        // Deletes 'blockcart-modal' from DOM because closing it only hides it,
        // causing errors down the line when trying to add other products.
        JS_EXEC.executeScript("return document.getElementById('blockcart-modal').remove();");

        LOGGER.info("========================================");
        return product;
    }

    static class ProductPreview extends AbstractUIObject {
        public ProductPreview(WebDriver driver, SearchContext searchContext) {
            super(driver, searchContext);
            PageFactory.initElements(new ExtendedElementLocatorFactory(driver, searchContext), this);
        }

        @FindBy(xpath = ".//h1[@class=\"h1\"]")
        WebElement productName;

        @FindBy(xpath = ".//*[@class=\"current-price\"]//span[@class=\"current-price-value\"]")
        WebElement priceTag;

        @FindBy(xpath = ".//button[@class=\"close\"]")
        WebElement closeBtn;

        @FindBy(xpath = ".//input[@id=\"quantity_wanted\"]")
        WebElement qtyInput;

        @FindBy(xpath = ".//button[contains(@class, \"add-to-cart\")]")
        WebElement addToCartBtn;
    }
}
