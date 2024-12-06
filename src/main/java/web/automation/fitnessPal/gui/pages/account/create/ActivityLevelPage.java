package web.automation.fitnessPal.gui.pages.account.create;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.automation.fitnessPal.gui.pages.account.common.SelectionPageBase;
import web.automation.fitnessPal.objects.account.enums.ActivityLevel;

import java.util.ArrayList;
import java.util.Set;

public class ActivityLevelPage extends SelectionPageBase<ActivityLevel> {
    public ActivityLevelPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/activity-level");
    }

    @Override
    public void select(Set<ActivityLevel> activityLevelSet) {
        if (activityLevelSet == null || activityLevelSet.size() != 1)
            throw  new IllegalArgumentException("Must select exactly one option.");

        ActivityLevel level = new ArrayList<>(activityLevelSet).getFirst();
        WebElement goalBtn = this.btnsContainer.findElement(By.xpath(".//button[" + level.getIndex() + "]"));
        goalBtn.click();

        this.clickNext();
    }
}
