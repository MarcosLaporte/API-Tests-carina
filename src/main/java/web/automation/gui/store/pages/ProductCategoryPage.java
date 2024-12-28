package web.automation.gui.store.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.components.ProductCard;
import web.automation.gui.store.objects.Product;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class ProductCategoryPage extends AbstractPage {
    public enum SortBy {
        SALES_DESC,
        RELEVANCE,
        NAME_ASC,
        NAME_DESC,
        PRICE_ASC,
        PRICE_DESC,
        REFERENCE_ASC,
        REFERENCE_DESC;

        public final int elementNumber;

        SortBy() {
            this.elementNumber = this.ordinal() + 1;
        }

        public final static Function<Integer, SortBy[]> getRandomSorts = amount -> {
            Random random = new Random();
            SortBy[] values = SortBy.values();

            return random.ints(amount, 0, values.length)
                    .mapToObj(i -> values[i])
                    .toArray(SortBy[]::new);
        };
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ProductCategoryPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @FindBy(xpath = "//*[@id=\"js-product-list-top\"]//div[contains(@class, \"products-sort-order\")]/button")
    public ExtendedWebElement sortDropdownBtn;

    @FindBy(xpath = "//div[contains(@class, \"products-sort-order\")]/div[@class=\"dropdown-menu\"]")
    private ExtendedWebElement sortMenu;

    @FindBy(xpath = "//*[@id=\"js-product-list\"]/div[@class=\"products row\"]")
    public ExtendedWebElement productsListEl;

    public LinkedList<Product> getAllProductsInOrder() {
        List<WebElement> productCardElList = productsListEl.findElements(By.xpath("./div"));
        LinkedList<Product> products = new LinkedList<>();

        for (int i = 0; i < productCardElList.size(); i++) {
            WebElement prodCartSearchContext = productsListEl.findElement(By.xpath("./div[" + (i + 1) + "]/article"));
            ProductCard currentCard = new ProductCard(getDriver(), prodCartSearchContext);

            products.add(new Product(currentCard.getProductName(), currentCard.getProductPriceTag()));
        }

        return products;
    }

    public void sortProducts(SortBy sortBy) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(
                ExpectedConditions.elementToBeClickable(this.sortDropdownBtn)
        ).click();

        WebElement sortByBtn = wait.until(
                ExpectedConditions.visibilityOf(this.sortMenu)
                        .andThen(menu -> menu.findElement(By.xpath(".//a[" + sortBy.elementNumber + "]")))
        );

        String expectedUrl = sortByBtn.getAttribute("href");

        Actions actions = new Actions(getDriver());
        actions.scrollToElement(sortByBtn);
        actions.moveToElement(sortByBtn);
        actions.perform();

        if (getCurrentUrl().equals(expectedUrl)) {
            LOGGER.info("Products are already sorted by {}", sortByBtn.getText());
            this.sortDropdownBtn.click();
            return;
        }

        LOGGER.info("Sorting products by {}", sortByBtn.getText());
        sortByBtn.click();

        boolean isUrlUpdated = wait.until(ExpectedConditions.urlToBe(expectedUrl));

        if (!isUrlUpdated)
            throw new RuntimeException("Page wasn't updated.");
    }

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().contains("controller=category");
    }

}