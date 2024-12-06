package web.automation.fitnessPal.gui.pages.account.create;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;

public class LastStepPage extends CreatePageBase {
    public LastStepPage(WebDriver driver) {
        super(driver);

        this.acceptAllBtn = this.formElement.findElement(By.xpath(".//h2[text()=\"Accept All\"]/../..//input[@type=\"checkbox\"]"));
    }

    public final WebElement acceptAllBtn;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/consents");
    }

    public NutritionalGoal acceptAllAndFinish() {
        this.acceptAllBtn.click();
        this.clickNext();
        return new NutritionalGoal(getDriver());
    }
}
