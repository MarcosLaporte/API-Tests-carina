package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.gui.fitnessPal.AccountFieldValidator;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.account.common.CreatePageBase;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public class SignUpPage extends CreatePageBase {
    @Context(dependsOn = "formElement")
    @FindBy(xpath = "//h1")
    private ExtendedWebElement headerMessage;

    public SignUpPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(headerMessage);
    }

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//input[@id=\"Email address\"]")
    public ExtendedWebElement emailInput;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//input[@id=\"Create a password\"]")
    public ExtendedWebElement passInput;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//p[text()=\"Terms & Conditions\"]/..")
    public ExtendedWebElement termsConditionsBtn;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//button[@type=\"submit\"]")
    public ExtendedWebElement continueBtn;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && headerMessage.isPresent();
    }

    public UsernameInputPage fillFieldsAndContinue(String emailAddress, String password) {
        AccountFieldValidator.validateEmail(emailAddress);
        this.emailInput.type(emailAddress);

        AccountFieldValidator.validatePassword(password);
        this.passInput.type(password);

        this.termsConditionsBtn.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(2500));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//form//p[contains(text(), \"Your email is already registered\")]")
            ));

            throw new IllegalArgumentException(emailAddress + " is already registered.");
        } catch (TimeoutException e) {
            Utils.LOGGER.debug("Email address is not in use.");
        }

        wait.until(ExpectedConditions.elementToBeClickable(this.continueBtn)).click();

        return new UsernameInputPage(getDriver());
    }

}
