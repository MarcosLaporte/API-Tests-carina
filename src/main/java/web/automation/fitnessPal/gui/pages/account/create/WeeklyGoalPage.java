package web.automation.fitnessPal.gui.pages.account.create;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.automation.fitnessPal.gui.pages.account.common.SelectionPageBase;
import web.automation.fitnessPal.objects.account.enums.WeeklyGoal;

import java.util.ArrayList;
import java.util.Set;

public class WeeklyGoalPage extends SelectionPageBase<WeeklyGoal> {
    public WeeklyGoalPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/weekly-goal");
    }

    @Override
    public void select(Set<WeeklyGoal> weeklyGoalSet) {
        if (weeklyGoalSet == null || weeklyGoalSet.size() != 1)
            throw  new IllegalArgumentException("Must select exactly one option.");

        WeeklyGoal weeklyGoal = new ArrayList<>(weeklyGoalSet).getFirst();
        WebElement goalBtn = this.btnsContainer.findElement(By.xpath(".//button[" + weeklyGoal.getIndex() + "]"));
        goalBtn.click();

        this.clickNext();
    }
}