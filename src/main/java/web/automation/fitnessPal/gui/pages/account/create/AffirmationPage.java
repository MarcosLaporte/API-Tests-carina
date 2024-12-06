package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.fitnessPal.gui.pages.account.common.CreatePageBase;

public class AffirmationPage extends CreatePageBase {
    public AffirmationPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }


    @Override
    public boolean isPageOpened() {
        String currentUrl = this.getCurrentUrl();
        return super.isPageOpened() &&
                currentUrl.contains("/affirmation") || currentUrl.contains("/big-step");
    }

}