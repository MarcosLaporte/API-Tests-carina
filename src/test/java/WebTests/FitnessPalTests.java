package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import web.automation.fitnessPal.gui.pages.SignedInHomePage;
import web.automation.fitnessPal.gui.pages.SignedOffHomePage;
import web.automation.fitnessPal.gui.pages.account.create.*;
import web.automation.fitnessPal.objects.account.Account;
import web.automation.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;

import java.text.ParseException;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static web.automation.fitnessPal.Utils.LOGGER;

public class FitnessPalTests implements IAbstractTest {
    @DataProvider(name = "account")
    public Object[][] getAccount() throws ParseException {
        return new Object[][]{
                {PropertiesReader.getAccount()}
        };
    }

    /*
     * Open https://www.myfitnesspal.com/
     * Click "Sign Up" button
     * Click "Continue" button on the 'Sign Up' screen
     * Go through all the registration steps
     */
    @Test(dataProvider = "account")
    public void test1(Account account) {
        WebDriver driver = this.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        SignedOffHomePage signedOffHomePage = new SignedOffHomePage(driver);
        signedOffHomePage.open();
        Assert.assertTrue(signedOffHomePage.isPageOpened());

        WelcomePage welcomePage = signedOffHomePage.clickStart();
        Assert.assertTrue(welcomePage.isPageOpened());

        FirstNameInputPage firstNamePage = welcomePage.openCreateAccount();
        Assert.assertTrue(firstNamePage.isPageOpened());

        GoalSelectionPage goalSelectionPage = firstNamePage.setNameInputAndContinue(account.firstName);
        Assert.assertTrue(goalSelectionPage.isPageOpened());

        Set<Goal> goals = account.goalsOptionsMap.keySet();
        goalSelectionPage.select(goals);

        AffirmationPage bigStepPage = new AffirmationPage(driver);
        Assert.assertTrue(bigStepPage.isPageOpened());
        bigStepPage.clickNext();

        for (Map.Entry<Goal, Set<GoalOptions>> goalSetEntry : account.goalsOptionsMap.entrySet()) {
            Goal goal = goalSetEntry.getKey();
            GoalOptionsPage<GoalOptions> goalOptionsPage = new GoalOptionsPage<>(driver, goal);
            Assert.assertTrue(goalOptionsPage.isPageOpened());

            goalOptionsPage.select(goalSetEntry.getValue());

            AffirmationPage affirmationPage = new AffirmationPage(driver);
            Assert.assertTrue(affirmationPage.isPageOpened());
            affirmationPage.clickNext();
        }

        ActivityLevelPage activityLevelPage = new ActivityLevelPage(driver);
        Assert.assertTrue(activityLevelPage.isPageOpened());
        activityLevelPage.select(Set.of(account.activityLevel));

        PersonalInfoPage personalInfoPage = new PersonalInfoPage(driver);
        Assert.assertTrue(personalInfoPage.isPageOpened());

        PhysicalInfoPage physicalInfoPage =
                personalInfoPage.fillFields(account.sex, account.dateOfBirth, account.countryOfOrigin);
        Assert.assertTrue(physicalInfoPage.isPageOpened());
        physicalInfoPage.fillFields(account.heightFt, account.heightIn, account.weightLb, account.goalWeight);

        if (goals.contains(Goal.LOSE_WEIGHT) || goals.contains(Goal.GAIN_WEIGHT)) {
            WeeklyGoalPage weeklyGoalPage = new WeeklyGoalPage(driver);
            Assert.assertTrue(weeklyGoalPage.isPageOpened());
            weeklyGoalPage.select(Set.of(account.weeklyGoal));
        }

        SignUpPage signUpPage = new SignUpPage(driver);
        Assert.assertTrue(signUpPage.isPageOpened());

        UsernameInputPage usernameInputPage =
                signUpPage.fillFields(account.email, account.password);
        Assert.assertTrue(usernameInputPage.isPageOpened());

        LastStepPage lastStepPage = usernameInputPage.setNameInput(account.username);
        Assert.assertTrue(lastStepPage.isPageOpened());
        // TODO? Add check for error message

        NutritionalGoal nutritionalGoal = lastStepPage.acceptAllAndFinish();
        Assert.assertTrue(nutritionalGoal.isPageOpened());

        SignedInHomePage homePage = nutritionalGoal.unsubscribeAndFinish();
        Assert.assertTrue(homePage.isPageOpened());

        LOGGER.info("Test ended.");
    }

    /*
     * Open https://www.myfitnesspal.com/
     * Click "Log In" button
     * Login using existing user
     * Go to Food page
     * Add breakfast food for today
     */
//    @Test
    public void test2() {

    }
}
