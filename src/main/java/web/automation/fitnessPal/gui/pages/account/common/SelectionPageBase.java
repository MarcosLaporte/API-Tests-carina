package web.automation.fitnessPal.gui.pages.account.common;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.fitnessPal.objects.account.enums.Options;

import java.time.Duration;
import java.util.Set;

public abstract class SelectionPageBase<T extends Options> extends CreatePageBase {
    public SelectionPageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.btnsContainer = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                this.formElement, By.xpath(".//div[@role=\"group\"]")
        ));
    }

    public WebElement btnsContainer;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened();
    }

    public abstract void select(Set<T> options);

}