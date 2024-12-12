package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = EditorialPage.class)
public class EditorialPage extends BasePage {
    public EditorialPage(WebDriver driver) {
        super(driver, Section.EDITORIAL);
        setUiLoadedMarker(editorialList);
    }

    @FindBy(id = "editorial_list")
    ExtendedWebElement editorialList;
}
