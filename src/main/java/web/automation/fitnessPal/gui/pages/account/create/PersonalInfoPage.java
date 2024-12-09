package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import web.automation.fitnessPal.Utils;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;
import web.automation.fitnessPal.objects.account.Account;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public class PersonalInfoPage extends CreatePageBase {
    public PersonalInfoPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/demographic-1");
    }

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[1]/div[2]//span[text()=\"%s\"]/..")
    public ExtendedWebElement sexBtn;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[2]//input[@id=\"birthday\"]")
    public ExtendedWebElement dobInput;

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//div/div[3]//div[@role=\"button\"]")
    public ExtendedWebElement countrySelect;

    @FindBy(xpath = "//*[@id=\"menu-\"]/div[3]/ul")
    private ExtendedWebElement countryList;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/demographic-1");
    }

    public PhysicalInfoPage fillFields(Account.Sex sex, Date dob, String country) {
        Utils.LOGGER.info("Filling personal info fields...");

        this.sexBtn.format(sex == Account.Sex.MALE ? "Male" : "Female").click();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        this.dobInput.type(dateFormat.format(dob));

        this.countrySelect.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(this.countryList.getBy()), Duration.ofSeconds(5));
        this.countryList
                .findElement(By.xpath(".//li[text()=\"" + country + "\"]"))
                .click();

        this.clickNext();

        return new PhysicalInfoPage(getDriver());
    }
}