package android.automation.aptoide.gui.pages;

import android.automation.aptoide.Utils;
import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = BasePage.class)
public abstract class BasePage extends AbstractPage implements IAndroidUtils {
    public enum Section {
        HOME("action_home", HomePage.class),
        EDITORIAL("action_curation", EditorialPage.class),
        SEARCH("action_search", SearchPage.class),
        STORES("action_stores", StoresPage.class),
        APPS("action_apps", AppsPage.class);

        public final String elementId;
        public final Class<? extends BasePage> pageClass;

        <T extends BasePage> Section(String elementId, Class<T> pageClass) {
            this.elementId = elementId;
            this.pageClass = pageClass;
        }
    }

    final Section section;

    public BasePage(WebDriver driver, Section section) {
        super(driver);
        this.section = section;
    }

    @FindBy(id = "bottom_navigation")
    private ExtendedWebElement bottomNav;

    @Context(dependsOn = "bottomNav")
    @FindBy(id = "%s")
    private ExtendedWebElement navBtn;

    public final ExtendedWebElement getBtn(Section btn) {
        return this.navBtn.format(btn.elementId);
    }

    public <T extends BasePage> T navigateTo(Section section) {
        Utils.LOGGER.info("Opening {} page...", section);
        ExtendedWebElement btnNav = this.getBtn(section);
        btnNav.click();

        try {
            @SuppressWarnings("unchecked")
            Class<T> sectionPageClass = (Class<T>) section.pageClass;
            Constructor<T> classConst = sectionPageClass.getDeclaredConstructor(WebDriver.class);
            return classConst.newInstance(getDriver());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPageOpened(Duration timeout) {
        ExtendedWebElement btnInNav = this.getBtn(this.section);
        String attr = btnInNav.getAttribute("selected");
        Utils.LOGGER.info("{} navbar button is selected: {}", this.section, attr.equals("true"));

        return super.isPageOpened(timeout) && attr.equals("true");
    }

}
