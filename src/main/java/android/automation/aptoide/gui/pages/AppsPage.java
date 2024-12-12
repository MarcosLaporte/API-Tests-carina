package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = AppsPage.class)
public class AppsPage extends BasePage {
    public AppsPage(WebDriver driver) {
        super(driver, Section.APPS);
        findExtendedWebElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_deny_button\"]")).clickIfPresent();

        setUiLoadedMarker(findExtendedWebElement(
                By.xpath("//android.view.ViewGroup[@resource-id=\"cm.aptoide.pt:id/fragment_apps_swipe_container\"]")
        ));
    }
}
