package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.account.common.SelectionPageBase;
import web.automation.gui.fitnessPal.objects.account.enums.ActivityLevel;

import java.util.ArrayList;
import java.util.Set;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SelectionPageBase.class)
public class ActivityLevelPage extends SelectionPageBase<ActivityLevel, PersonalInfoPage> {
    public ActivityLevelPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/activity-level");
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/activity-level");
    }

    @Override
    public PersonalInfoPage selectAndContinue(Set<ActivityLevel> activityLevelSet) {
        if (activityLevelSet == null || activityLevelSet.size() != 1)
            throw  new IllegalArgumentException("Must select exactly one option.");

        ActivityLevel level = new ArrayList<>(activityLevelSet).getFirst();
        ExtendedWebElement levelBtn = this.buttonEl.format(level.getIndex());
        Utils.scrollTo(getDriver(), levelBtn);
        levelBtn.click();

        this.clickNext();

        return new PersonalInfoPage(getDriver());
    }
}
