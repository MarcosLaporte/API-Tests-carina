package web.automation.gui.store.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import com.zebrunner.carina.webdriver.locator.ExtendedElementLocatorFactory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductAddedModal extends AbstractUIObject {

    public ProductAddedModal(WebDriver driver, SearchContext searchContext) {
        super(driver);
        PageFactory.initElements(new ExtendedElementLocatorFactory(driver, searchContext), this);
    }

    @FindBy(xpath = ".//h4[@id=\"myModalLabel\"]")
    public WebElement title;

    @FindBy(xpath = ".//button[@class=\"close\"]")
    public WebElement closeBtn;

    @Override
    public boolean isUIObjectPresent() {
        return getSearchContext() != null && title != null
                && title.getText().contains("Product successfully added to your shopping cart");
    }
}
