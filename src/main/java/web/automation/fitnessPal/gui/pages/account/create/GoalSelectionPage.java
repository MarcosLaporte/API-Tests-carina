package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.gui.pages.account.common.SelectionPageBase;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;

import java.util.Set;

public class GoalSelectionPage extends SelectionPageBase<Goal> {
    public GoalSelectionPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/goals");
    }

    @Override
    public void select(Set<Goal> goals) throws IllegalArgumentException {
        AccountFieldValidator.validateGoals(goals);

        for (Goal goal : goals) {
           WebElement goalBtn = this.btnsContainer.findElement(By.xpath(".//button[" + goal.getIndex() + "]"));
           goalBtn.click();
        }

        if (goals.size() < 3)
            this.clickNext();
    }

}