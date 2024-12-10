package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.Utils;
import web.automation.fitnessPal.gui.pages.account.common.SelectionPageBase;
import web.automation.fitnessPal.gui.pages.account.create.GoalAffirmationPage.AffirmationRelUrl;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;

import java.util.Set;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SelectionPageBase.class)
public class GoalSelectionPage extends SelectionPageBase<Goal, GoalAffirmationPage> {
    public GoalSelectionPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/goals");
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/goals");
    }

    @Override
    public GoalAffirmationPage selectAndContinue(Set<Goal> goals) throws IllegalArgumentException {
        AccountFieldValidator.validateGoals(goals);

        for (Goal goal : goals) {
            ExtendedWebElement currGoalBtn = this.buttonEl.format(goal.getIndex());
            Utils.scrollTo(getDriver(), currGoalBtn);
            currGoalBtn.click();
        }

        if (goals.size() < 3)
            this.clickNext();

        return new GoalAffirmationPage(getDriver(), AffirmationRelUrl.BIG_STEP);
    }

}