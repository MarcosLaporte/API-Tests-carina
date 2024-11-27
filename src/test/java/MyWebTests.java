import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import web.automation.gui.login.pages.SignInPage;
import web.automation.gui.login.pages.SuccessfulLoginPage;

public class MyWebTests implements IAbstractTest {
    @Test
    public void testSuccessfulLogin() {
        WebDriver driver = getDriver();
        SignInPage page = new SignInPage(driver);
        page.open();
        Assert.assertTrue(page.isPageOpened(), "Sign in page is not opened.");

        page.userInput.type("student", 3);
        page.passInput.type("Password123", 3);
        page.submitBtn.click(5);

        Assert.assertTrue(page.getCurrentUrl().contains("logged-in-successfully/"), "Login unsuccessful, page not redirected.");

        SuccessfulLoginPage successPage = new SuccessfulLoginPage(driver);
        Assert.assertTrue(successPage.isPageOpened(), "Successful login page is not opened.");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(successPage.successTitle.getText(), "Logged In Successfully");
        softAssert.assertTrue(successPage.logoutBtn.isElementPresent());

        softAssert.assertAll();
    }

    @Test
    public void testInvalidUsername() {
        WebDriver driver = getDriver();
        SignInPage page = new SignInPage(driver);
        page.open();
        Assert.assertTrue(page.isPageOpened(), "Sign in page is not opened.");

        page.userInput.type("INVALID-USERNAME");
        page.passInput.type("Password123");
        page.submitBtn.click(3);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(page.errorMsg.isElementPresent());
        softAssert.assertEquals(page.errorMsg.getText(), "Your username is invalid!");

        softAssert.assertAll();
    }

    @Test
    public void testInvalidPassword() {
        WebDriver driver = getDriver();
        SignInPage page = new SignInPage(driver);
        page.open();
        Assert.assertTrue(page.isPageOpened(), "Sign in page is not opened.");

        page.userInput.type("student");
        page.passInput.type("INVALID-PASSWORD");
        page.submitBtn.click(3);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(page.errorMsg.isElementPresent());
        softAssert.assertEquals(page.errorMsg.getText(), "Your password is invalid!");

        softAssert.assertAll();
    }

    @Test
    public void testSuccessfulLogout() {
        WebDriver driver = getDriver();
        SignInPage page = new SignInPage(driver);
        page.open();
        Assert.assertTrue(page.isPageOpened(), "Sign in page is not opened.");

        page.userInput.type("student", 3);
        page.passInput.type("Password123", 3);
        page.submitBtn.click(5);

        Assert.assertTrue(page.getCurrentUrl().contains("logged-in-successfully/"), "Login unsuccessful, page not redirected.");

        SuccessfulLoginPage successPage = new SuccessfulLoginPage(driver);
        Assert.assertTrue(successPage.isPageOpened(), "Successful login page is not opened.");
        Assert.assertTrue(successPage.logoutBtn.isElementPresent());

        successPage.logoutBtn.click(5);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(page.getCurrentUrl().contains("practice-test-login/"), "Logout unsuccessful, page not redirected.");
        softAssert.assertTrue(page.isPageOpened(), "Sign in page is not opened.");

        softAssert.assertAll();
    }
}
