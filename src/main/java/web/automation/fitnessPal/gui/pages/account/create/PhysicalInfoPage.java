package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhysicalInfoPage extends CreatePageBase {
    public PhysicalInfoPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);

        this.heightFtInput = this.formElement.findElement(By.xpath(".//div/div[1]/input[@id=\"Height (feet)\"]"));
        this.heightInInput = this.formElement.findElement(By.xpath(".//div/div[1]/input[@id=\"Height (inches)\"]"));
        this.currentWeightInput = this.formElement.findElement(By.xpath(".//div/div[2]/input[@id=\"Current weight\"]"));
        this.goalWeightInput = this.formElement.findElement(By.xpath(".//div/div[3]/input[@id=\"Current weight\"]"));
    }

    public final WebElement heightFtInput;
    public final WebElement heightInInput;
    public final WebElement currentWeightInput;
    public final WebElement goalWeightInput;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/demographic-2");
    }

    public void fillFields(float heightFt, float heightIn, float currentWeight, float goalWeight) {
        AccountFieldValidator.validateHeight(heightFt, heightIn);
        this.heightFtInput.sendKeys(Float.toString(heightFt));
        this.heightFtInput.sendKeys(Float.toString(heightIn));

        AccountFieldValidator.validateWeight(currentWeight);
        this.currentWeightInput.sendKeys(Float.toString(currentWeight));

        this.goalWeightInput.clear();
        this.goalWeightInput.sendKeys(Float.toString(goalWeight));

        WebElement goalWeightErrorMsg = this.formElement.findElement(By.xpath(".//div/div[3]/p[3]"));
        if (goalWeightErrorMsg.isDisplayed()) {
            this.goalWeightInput.clear();
            goalWeight = this.getRecommendedWeight();
            this.goalWeightInput.sendKeys(Float.toString(goalWeight));
        }

        this.clickNext();
    }

    private float getRecommendedWeight() {
        WebElement goalWeightErrorMsg = this.formElement.findElement(By.xpath(".//div/div[3]/p[3]"));
        Pattern pattern = Pattern.compile("[\\d.]+");
        Matcher matcher = pattern.matcher(goalWeightErrorMsg.getText());
        if (matcher.find())
            return Float.parseFloat(matcher.group());

        throw new RuntimeException("Error message doesn't display any recommended weight.");
    }
}