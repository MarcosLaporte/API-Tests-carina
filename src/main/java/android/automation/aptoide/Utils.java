package android.automation.aptoide;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Utils {
    public static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void clearWebField(ExtendedWebElement element) {
        element.type(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public static void scrollTo(WebDriver driver, ExtendedWebElement element) {
        scrollTo(driver, element.getElement());
    }

    public static void scrollTo(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({ block: 'center' });", element);
    }
}