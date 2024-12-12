package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = HomePage.class)
public class HomePage extends BasePage implements IMobileUtils {
    public HomePage(WebDriver driver) {
        super(driver, Section.HOME);
        setUiLoadedMarker(mainContent);
    }

    @FindBy(id = "main_home_container_content")
    ExtendedWebElement mainContent;
}
