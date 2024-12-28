package android.automation.aptoide.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AppPage extends BasePage {
    public AppPage(WebDriver driver) {
        super(driver, Section.SEARCH);
        setUiLoadedMarker(installBtn);
    }

    @FindBy(id = "appview_install_button")
    ExtendedWebElement installBtn;

    @FindBy(id = "app_name")
    public ExtendedWebElement appNameEl;

}
