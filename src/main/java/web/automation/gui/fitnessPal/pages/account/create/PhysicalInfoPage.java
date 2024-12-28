package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.gui.fitnessPal.AccountFieldValidator;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.account.common.CreatePageBase;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public class PhysicalInfoPage extends CreatePageBase {
    public PhysicalInfoPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/demographic-2");
    }

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[1]//input[@id=\"Height (feet)\"]")
    public ExtendedWebElement heightFtInput;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[1]//input[@id=\"Height (inches)\"]")
    public ExtendedWebElement heightInInput;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[2]//input[@id=\"Current weight\"]")
    public ExtendedWebElement currentWeightInput;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[3]//input[@id=\"Goal weight\"]")
    public ExtendedWebElement goalWeightInput;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/demographic-2");
    }

    public void fillFieldsAndContinue(float heightFt, float heightIn, float currentWeight, float goalWeight) {
        AccountFieldValidator.validateHeight(heightFt, heightIn);
        this.heightFtInput.type(Float.toString(heightFt));
        this.heightInInput.type(Float.toString(heightIn));

        AccountFieldValidator.validateWeight(currentWeight);
        this.currentWeightInput.type(Float.toString(currentWeight));

        // Replacement of element.clear() method, which is not implemented for ExtendedWebElement
        Utils.clearWebField(this.goalWeightInput);
        this.goalWeightInput.type(Float.toString(goalWeight));

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(2500));
            WebElement goalWeightErrorMsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(".//div/div[3]/p[3]"))
            );

            if (goalWeightErrorMsg.isDisplayed()) {
                Utils.clearWebField(this.goalWeightInput);
                goalWeight = this.getRecommendedWeight(goalWeightErrorMsg.getText());

                this.goalWeightInput.type(Float.toString(goalWeight));
            }
        } catch (TimeoutException _) {
            Utils.LOGGER.debug("Custom goal weight was accepted.");
        }

        this.clickNext();
    }

    private float getRecommendedWeight(String errorMsg) {
        Pattern pattern = Pattern.compile("[\\d.]+");
        Matcher matcher = pattern.matcher(errorMsg);
        if (matcher.find())
            return Float.parseFloat(matcher.group());

        throw new RuntimeException("Error message doesn't display any recommended weight.");
    }
}