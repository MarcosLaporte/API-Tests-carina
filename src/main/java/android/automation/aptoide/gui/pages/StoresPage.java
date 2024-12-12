package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = StoresPage.class)
public class StoresPage extends BasePage {
    public StoresPage(WebDriver driver) {
        super(driver, Section.STORES);
        setUiLoadedMarker(recyclerView);
    }

    @FindBy(id = "recycler_view")
    ExtendedWebElement recyclerView;
}
