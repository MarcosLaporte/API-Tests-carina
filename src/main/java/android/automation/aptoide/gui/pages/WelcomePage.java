package android.automation.aptoide.gui.pages;

import android.automation.aptoide.Utils;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class WelcomePage extends AbstractPage implements IMobileUtils {
    public WelcomePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(title);
    }

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"cm.aptoide.pt:id/title\"]")
    private ExtendedWebElement title;

    @FindBy(id = "skip_button")
    private ExtendedWebElement skipBtn;

    public HomePage skipWelcomePage() {
        this.skipBtn.click();
        Utils.LOGGER.info("Skipping welcome page...");

        return new HomePage(getDriver());
    }
}
