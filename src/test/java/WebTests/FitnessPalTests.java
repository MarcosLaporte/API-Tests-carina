package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import web.automation.fitnessPal.gui.pages.SignedInHomePage;
import web.automation.fitnessPal.gui.pages.SignedOffHomePage;
import web.automation.fitnessPal.gui.pages.account.create.*;
import web.automation.fitnessPal.objects.account.Account;
import web.automation.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

        SignedOffHomePage signedOffHomePage = new SignedOffHomePage(driver);
        signedOffHomePage.open();
        signedOffHomePage.assertPageOpened();
        signedOffHomePage.acceptCookies();

        WelcomePage welcomePage = signedOffHomePage.clickStart();
        welcomePage.assertPageOpened();

        FirstNameInputPage firstNamePage = welcomePage.openCreateAccount();
        firstNamePage.assertPageOpened();

        GoalSelectionPage goalSelectionPage = firstNamePage.setNameInputAndContinue(account.firstName);
        goalSelectionPage.assertPageOpened();

        Set<Goal> goals = account.goalsOptionsMap.keySet();
        GoalAffirmationPage bigStepPage = goalSelectionPage.selectAndContinue(goals);
        bigStepPage.assertPageOpened();
        bigStepPage.clickNext();

        // Sorts goals by index because Sets do not provide any order.
        List<Goal> sortedGoals = new ArrayList<>(goals);
        sortedGoals.sort(Comparator.comparing(Goal::getIndex));
        for (Goal goal : sortedGoals) {
            GoalOptionsPage<GoalOptions> goalOptionsPage = new GoalOptionsPage<>(driver, goal);
            goalOptionsPage.assertPageOpened();

            GoalAffirmationPage affirmationPage = goalOptionsPage.selectAndContinue(account.goalsOptionsMap.get(goal));
            affirmationPage.assertPageOpened();
            affirmationPage.clickNext();
        }

        ActivityLevelPage activityLevelPage = new ActivityLevelPage(driver);
        activityLevelPage.assertPageOpened();

        PersonalInfoPage personalInfoPage = activityLevelPage.selectAndContinue(Set.of(account.activityLevel));
        personalInfoPage.assertPageOpened();

        PhysicalInfoPage physicalInfoPage =
                personalInfoPage.fillFields(account.sex, account.dateOfBirth, account.countryOfOrigin);
        physicalInfoPage.assertPageOpened();
        physicalInfoPage.fillFieldsAndContinue(account.heightFt, account.heightIn, account.weightLb, account.goalWeight);

        SignUpPage signUpPage = null;

        if (goals.contains(Goal.LOSE_WEIGHT) || goals.contains(Goal.GAIN_WEIGHT)) {
            WeeklyGoalPage weeklyGoalPage = new WeeklyGoalPage(driver);
            weeklyGoalPage.assertPageOpened();
            signUpPage = weeklyGoalPage.selectAndContinue(Set.of(account.weeklyGoal));
        }

        if (signUpPage == null)
            signUpPage = new SignUpPage(driver);
        signUpPage.assertPageOpened();

        UsernameInputPage usernameInputPage = signUpPage.fillFieldsAndContinue(account.email, account.password);
        usernameInputPage.assertPageOpened();

        LastStepPage lastStepPage = usernameInputPage.continueDefaultName();
        lastStepPage.assertPageOpened();

        NutritionalGoal nutritionalGoal = lastStepPage.acceptAllAndFinish();
        nutritionalGoal.assertPageOpened();

        SignedInHomePage homePage = nutritionalGoal.unsubscribeAndFinish();
        homePage.assertPageOpened();
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
