package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;

import java.time.Duration;

public class SignUpPage extends CreatePageBase {
    @FindBy(xpath = "//h1[text()=\"Almost there! Create your account.\"]")
    private ExtendedWebElement headerMessage;

    public SignUpPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(headerMessage);

        this.emailInput = this.formElement.findElement(By.xpath(".//input[@id=\"Email address\"]"));
        this.passInput = this.formElement.findElement(By.xpath(".//input[@id=\"Create a password\"]"));
        this.termsConditionsBtn = this.formElement.findElement(By.xpath(".//input[@type=\"checkbox\"]"));
        this.continueBtn = this.formElement.findElement(By.xpath(".//button[@type=\"submit\"]"));
    }

    public final WebElement emailInput;
    public final WebElement passInput;
    public final WebElement termsConditionsBtn;
    public final WebElement continueBtn;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && headerMessage.isPresent();
    }

    public UsernameInputPage fillFields(String emailAddress, String password) {
        AccountFieldValidator.validateEmail(emailAddress);
        this.emailInput.sendKeys(emailAddress);

        AccountFieldValidator.validatePassword(password);
        this.passInput.sendKeys(password);

        this.termsConditionsBtn.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(this.continueBtn)).click();

        return new UsernameInputPage(getDriver());
    }

}
