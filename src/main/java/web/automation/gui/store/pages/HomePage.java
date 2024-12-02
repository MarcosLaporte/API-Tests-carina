package web.automation.gui.store.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.automation.gui.store.components.ListedProducts;
import web.automation.gui.store.components.ProductCard;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class HomePage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//*[@id=\"carousel\"]")
    private ExtendedWebElement carouselEl;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(carouselEl);
    }

    @Override
    public void open() {
        getDriver().navigate().to("https://teststore.automationtesting.co.uk/");
    }

    /*public ListedProducts getProductsInList(ListedProducts.ProductList productList) {
        LOGGER.info("Getting listed products from {} list...", productList);

        String xPath = "//section[contains(@class, \"featured-products\")]//h2[normalize-space(text())=\"" + productList + "\"]/..";
        WebElement listSectionEl = getDriver().findElement(By.xpath(xPath));

        if (listSectionEl == null)
            LOGGER.warn("List element was not found.");

        return new ListedProducts(getDriver(), listSectionEl);
    }*/

    @FindBy(xpath = "//section[contains(@class, \"featured-products\")]//h2[normalize-space(text())=\"%s\"]/..//article")
    public List<ExtendedWebElement> productsInList;

    public List<ProductCard> getProductsInList(ListedProducts.ProductList productList) {
        LOGGER.info("Getting listed products from {} list...", productList);

        String xPath = "//section[contains(@class, \"featured-products\")]//h2[normalize-space(text())=\"" + productList + "\"]/..//article";
        List<WebElement> listSectionEl = getDriver().findElements(By.xpath(xPath));

        if (listSectionEl == null) {
            LOGGER.warn("List element was not found.");
            return List.of();
        }

        return listSectionEl.stream()
                .map(el -> new ProductCard(getDriver(), el))
                .toList();
    }

    public List<ProductCard> selectProducts(ListedProducts.ProductList productList, int amount) {
        if (amount < 1) return List.of();

          /*ListedProducts listedProducts = this.getProductsInList(productList);
          listedProducts.scrollTo();
          List<SearchContext> productCardList = listedProducts.productElementList;*/

        String xPath = "//section[contains(@class, \"featured-products\")]//h2[normalize-space(text())=\"" + productList + "\"]/..//article";
        List<WebElement> productCardElements = getDriver().findElements(By.xpath(xPath));

        List<ProductCard> productCardList = productCardElements
                .stream()
                .map(el -> new ProductCard(getDriver(), el))
                .toList();

        if (amount > productCardList.size())
            throw new RuntimeException("There aren't enough products in " + productList + " list.");

        if (productCardList.size() == 1)
            return List.of(productCardList.getFirst());

        LOGGER.info("Selecting {} products...", amount);

        List<ProductCard> selectedProdCards = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            ProductCard card;
            do {
                int randProdIndex = random.nextInt(0, productCardList.size() - 1);
                card = productCardList.get(randProdIndex);
            } while (selectedProdCards.contains(card));

            selectedProdCards.add(card);
            LOGGER.info("{}. {}", i + 1, card.productName.getText());
        }

        return selectedProdCards;
    }

}
