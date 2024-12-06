package web.automation.fitnessPal.gui.pages.account.common;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class SingleInputPageBase extends CreatePageBase {
    public SingleInputPageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);

        this.inputEl = this.formElement.findElement(By.xpath(".//input"));
    }

    public final WebElement inputEl;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened();
    }

}
