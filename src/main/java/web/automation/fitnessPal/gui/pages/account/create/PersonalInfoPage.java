package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;
import web.automation.fitnessPal.objects.account.Account;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

public class PersonalInfoPage extends CreatePageBase {
    public PersonalInfoPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);

        this.sexBtns = this.getSexBtns();
        this.dobInput = this.formElement.findElement(By.xpath(".//div/div[2]//input[@id=\"birthday\"]"));
        this.countrySelect = this.formElement.findElement(By.xpath(".//div/div[3]//div[@role=\"button\"]"));

    }

    private Map<Account.Sex, WebElement> getSexBtns() {
        WebElement btnsDiv = this.formElement.findElement(By.xpath(".//div/div[1]/div[2]"));
        return Map.of(
                Account.Sex.MALE, btnsDiv.findElement(By.xpath(".//input[@value=\"M\"]")),
                Account.Sex.FEMALE, btnsDiv.findElement(By.xpath(".//input[@value=\"F\"]"))
        );
    }

    public final Map<Account.Sex, WebElement> sexBtns;
    public final WebElement dobInput;
    public final WebElement countrySelect;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/demographic-1");
    }

    public PhysicalInfoPage fillFields(Account.Sex sex, Date dob, String country) {
        this.sexBtns.get(sex).click();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        this.dobInput.sendKeys(dateFormat.format(dob));

        this.countrySelect.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement countryList = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"menu-\"]/div[3]/ul/")
        ));
        WebElement selectedCountry = countryList.findElement(By.xpath(".//li[text()=\"" + country + "\"]"));
        selectedCountry.click();

        this.clickNext();

        return new PhysicalInfoPage(getDriver());
    }
}