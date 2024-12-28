package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.WebDriver;
import web.automation.gui.fitnessPal.pages.account.common.SelectionPageBase;
import web.automation.gui.fitnessPal.objects.account.enums.WeeklyGoal;

import java.util.ArrayList;
import java.util.Set;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SelectionPageBase.class)
public class WeeklyGoalPage extends SelectionPageBase<WeeklyGoal, SignUpPage> {
    public WeeklyGoalPage(WebDriver driver) {
        super(driver);
        setPageURL("/weekly-goal");
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/weekly-goal");
    }

    @Override
    public SignUpPage selectAndContinue(Set<WeeklyGoal> weeklyGoalSet) {
        if (weeklyGoalSet == null || weeklyGoalSet.size() != 1)
            throw new IllegalArgumentException("Must select exactly one option.");

        WeeklyGoal weeklyGoal = new ArrayList<>(weeklyGoalSet).getFirst();
        this.buttonEl.format(weeklyGoal.getIndex()).click();

        this.clickNext();

        return new SignUpPage(getDriver());
    }
}