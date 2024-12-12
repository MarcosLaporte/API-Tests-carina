package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = HomePage.class)
public class HomePage extends BasePage implements IAndroidUtils {
    public HomePage(WebDriver driver) {
        super(driver, Section.HOME);
        setUiLoadedMarker(mainContent);
    }

    @FindBy(id = "main_home_container_content")
    ExtendedWebElement mainContent;

    @FindBy(id = "games_chip")
    public ExtendedWebElement gamesChipBtn;

    @FindBy(id = "apps_chip")
    public ExtendedWebElement appsChipBtn;

    @FindBy(id = "featured_graphic_list")
    public ExtendedWebElement featuredAppList;

    @FindBy(id = "root_cardview")
    public ExtendedWebElement gotwCardEl;

    @FindBy(id = "featured_appc_card")
    public ExtendedWebElement featuredAppCoinList;
}
