package web.automation.fitnessPal.gui.pages.account.common;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class CreatePageBase extends AbstractPage {

    public CreatePageBase(WebDriver driver) {
        super(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.formElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//section/div/main/div/form")
        ));
    }

    public final WebElement formElement;

    @FindBy(xpath = "//section/div/main/div/form//nav/a")
    private ExtendedWebElement formBackBtn;

    @FindBy(xpath = "//section/div/main/div/form//nav//button")
    private ExtendedWebElement formNextBtn;

    public void clickBack() {
        this.formBackBtn.click(3);
    }

    public void clickNext() {
        this.formNextBtn.click(3);
    }

    @Override
    public boolean isPageOpened() {
        return this.getCurrentUrl().contains("/account/create");
    }
}
