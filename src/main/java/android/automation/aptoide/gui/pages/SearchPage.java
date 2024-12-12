package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = SearchPage.class)
public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver) {
        super(driver, Section.SEARCH);
        this.hideKeyboard();

        setUiLoadedMarker(trendingList);
    }

    @FindBy(id = "trending_list")
    ExtendedWebElement trendingList;
}
