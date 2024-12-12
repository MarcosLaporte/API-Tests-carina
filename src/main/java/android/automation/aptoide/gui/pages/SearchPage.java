package android.automation.aptoide.gui.pages;

import android.automation.aptoide.Utils;
import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = SearchPage.class)
public class SearchPage extends BasePage implements IAndroidUtils {
    public SearchPage(WebDriver driver) {
        super(driver, Section.SEARCH);
        this.hideKeyboard();

        setUiLoadedMarker(trendingList);
    }

    @FindBy(id = "trending_list")
    ExtendedWebElement trendingList;

    @FindBy(id = "search_src_text")
    ExtendedWebElement searchInput;

    @FindBy(id = "fragment_search_result_all_stores_app_list")
    ExtendedWebElement appResultList;

    public List<ExtendedWebElement> searchFor(String query, int limit) {
        this.searchInput.click();
        this.searchInput.type(query);
        pressKeyboardKey(AndroidKey.ENTER);

        waitUntil(ExpectedConditions.presenceOfElementLocated(appResultList.getBy()), Duration.ofSeconds(5));
        List<ExtendedWebElement> resultList = this.appResultList.findExtendedWebElements(
                By.xpath(".//android.view.ViewGroup")
        );

        if (limit > resultList.size())
            limit = resultList.size();

        return resultList.subList(0, limit);
    }

    public static String getAppName(ExtendedWebElement appResult) {
        return appResult.findExtendedWebElement(
                By.xpath("//android.widget.TextView[@resource-id=\"cm.aptoide.pt:id/app_name\"]")
        ).getText();
    }

    public AppPage openResult(ExtendedWebElement appResult) {
        Utils.LOGGER.info("Selecting {}...", getAppName(appResult));
        appResult.click();

        return new AppPage(getDriver());
    }
}
