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
import web.automation.gui.store.components.ProductCard;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class HomePage extends AbstractPage {
    public enum FeaturedList {
        POPULAR("Popular Products"),
        SALE("On sale"),
        NEW("New products");

        public final String displayText;

        FeaturedList(String displayText) {
            this.displayText = displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//*[@id=\"carousel\"]")
    public ExtendedWebElement carouselEl;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(carouselEl);
    }

    @FindBy(xpath = "//*[@id=\"content\"]/a[@class=\"banner\"]")
    public ExtendedWebElement bannerEl;

    @FindBy(id = "custom-text")
    public ExtendedWebElement customTextBlockEl;

    @FindBy(id = "footer")
    public ExtendedWebElement footerEl;

    public UnaryOperator<String> getFeaturedListPath = featuredList -> "//section[contains(@class, \"featured-products\")]//h2[normalize-space(text())=\"" + featuredList + "\"]";

    public List<ProductCard> getFeaturedProducts(FeaturedList featuredList) {
        LOGGER.info("Getting listed products from {} list...", featuredList);

        String xPath = getFeaturedListPath.apply(featuredList.displayText) + "/..//article";
        List<WebElement> listSectionEl = getDriver().findElements(By.xpath(xPath));

        if (listSectionEl == null || listSectionEl.isEmpty()) {
            LOGGER.warn("List element was not found or was empty.");
            return List.of();
        }

        return listSectionEl.stream()
                .map(el -> new ProductCard(getDriver(), el))
                .toList();
    }

    public List<ProductCard> selectProducts(FeaturedList featuredList, int amount) {
        if (amount < 1) return List.of();

        List<WebElement> productCardElements = getDriver().findElements(
                By.xpath(getFeaturedListPath.apply(featuredList.displayText) + "/..//article")
        );

        List<ProductCard> productCardList = productCardElements
                .stream()
                .map(el -> new ProductCard(getDriver(), el))
                .toList();

        if (amount > productCardList.size())
            throw new RuntimeException("There aren't enough products in " + featuredList + " list.");

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
            LOGGER.info("{}. {}", i + 1, card.getProductName());
        }

        return selectedProdCards;
    }

}
