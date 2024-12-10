package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public class GoalAffirmationPage extends CreatePageBase {
    public enum AffirmationRelUrl {
        AFFIRMATION("affirmation"),
        BIG_STEP("big-step");

        private final String url;
        AffirmationRelUrl(String url) {
            this.url = url;
        }
    }

    private AffirmationRelUrl affirmationRelUrl;
    public GoalAffirmationPage(WebDriver driver, Goal goal, AffirmationRelUrl affirmationRelUrl) {
        this(driver, goal.relUrl + '/' + affirmationRelUrl.url);
        this.affirmationRelUrl = affirmationRelUrl;
    }

    public GoalAffirmationPage(WebDriver driver, AffirmationRelUrl affirmationRelUrl) {
        this(driver, affirmationRelUrl.url);
        this.affirmationRelUrl = affirmationRelUrl;
    }

    private GoalAffirmationPage(WebDriver driver, String url) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/goals/" + url);
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains(affirmationRelUrl.url);
    }

}