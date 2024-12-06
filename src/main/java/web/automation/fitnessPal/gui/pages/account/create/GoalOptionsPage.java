package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.gui.pages.account.common.SelectionPageBase;
import web.automation.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;

import java.util.Set;

public class GoalOptionsPage<T extends GoalOptions> extends SelectionPageBase<T> {
    public final Goal goal;

    public GoalOptionsPage(WebDriver driver, Goal goal) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);

        this.goal = goal;
    }

    @Override
    public boolean isPageOpened() {
        return this.getCurrentUrl().contains("/account/create/goals");
    }

    @Override
    public void select(Set<T> options) throws IllegalArgumentException {
        AccountFieldValidator.validateGoalOptions(this.goal, options);

        for (T option : options) {
            WebElement optionBtn = this.btnsContainer.findElement(By.xpath(".//button[" + option.getIndex() + "]"));
            optionBtn.click();
        }

        if (options.size() < this.btnsContainer.findElements(By.xpath(".//button")).size())
            this.clickNext();
    }
}