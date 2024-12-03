package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import web.automation.gui.login.pages.SignInPage;
import web.automation.gui.login.pages.SuccessfulLoginPage;

public class LoginPageTests implements IAbstractTest {
    @Test
    public void testSuccessfulLogin() {
        WebDriver driver = getDriver();

        SignInPage signInPage = new SignInPage(driver);
        signInPage.open();
        Assert.assertTrue(signInPage.isPageOpened(), "Sign in page was not opened.");

        signInPage.userInput.type("student", 3);
        signInPage.passInput.type("Password123", 3);
        signInPage.submitBtn.click(5);

        Assert.assertTrue(signInPage.getCurrentUrl().contains("logged-in-successfully/"), "Login unsuccessful, page not redirected.");

        SuccessfulLoginPage successPage = new SuccessfulLoginPage(driver);
        Assert.assertTrue(successPage.isPageOpened(), "Successful login page was not opened.");

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
        Assert.assertTrue(page.isPageOpened(), "Sign in page was not opened.");

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
        Assert.assertTrue(page.isPageOpened(), "Sign in page was not opened.");

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

        SuccessfulLoginPage successPage = new SuccessfulLoginPage(driver);
        successPage.open();
        Assert.assertTrue(successPage.isPageOpened(), "Successful login page was not opened.");

        Assert.assertTrue(successPage.logoutBtn.isElementPresent());

        successPage.logoutBtn.click(5);

        Assert.assertTrue(successPage.getCurrentUrl().contains("practice-test-login/"), "Logout unsuccessful, page not redirected.");

        SignInPage signInPage = new SignInPage(driver);
        Assert.assertTrue(signInPage.isPageOpened(), "Sign in page was not opened.");
    }
}
