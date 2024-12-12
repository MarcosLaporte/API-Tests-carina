package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = StoresPage.class)
public class StoresPage extends BasePage {
    public StoresPage(WebDriver driver) {
        super(driver, Section.STORES);
        setUiLoadedMarker(recyclerView);
    }

    @FindBy(id = "recycler_view")
    ExtendedWebElement recyclerView;

    @Context(dependsOn = "recyclerView")
    @FindBy(id = "title")
    public List<ExtendedWebElement> headerList;

    @Context(dependsOn = "recyclerView")
    @FindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"cm.aptoide.pt:id/recycler_view\"]/android.widget.FrameLayout/android.widget.RelativeLayout")
    public List<ExtendedWebElement> recommendedStores;

}
