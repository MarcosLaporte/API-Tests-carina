package web.automation.fitnessPal.objects.account;

import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.objects.account.enums.ActivityLevel;
import web.automation.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.fitnessPal.objects.account.enums.WeeklyGoal;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static web.automation.fitnessPal.objects.account.enums.Goals.Goal;

public class Account {
    public enum Sex {MALE, FEMALE}

    public final String firstName;
    public final Map<Goal, Set<GoalOptions>> goalsOptionsMap;
    public final ActivityLevel activityLevel;
    public final Sex sex;
    public final Date dateOfBirth;
    public final String countryOfOrigin;
    public final float heightFt;
    public final float heightIn;
    public final float weightLb;
    public final float goalWeight;
    public final WeeklyGoal weeklyGoal;
    public final String email;
    public final String password;
    public final String username;

    public Account(String firstName, Map<Goal, Set<GoalOptions>> goalsOptionsMap, ActivityLevel activityLevel, Sex sex, Date dateOfBirth, String countryOfOrigin, float heightFt, float heightIn, float weightLb, float goalWeight, WeeklyGoal weeklyGoal, String email, String password, String username) {
        AccountFieldValidator.validateFirstName(firstName);
        AccountFieldValidator.validateGoalsAndOptions(goalsOptionsMap);
        AccountFieldValidator.validateAge(dateOfBirth);
        AccountFieldValidator.validateHeight(heightFt, heightIn);
        AccountFieldValidator.validateWeight(weightLb);
        AccountFieldValidator.validateGoalWeight(goalsOptionsMap.keySet(), weightLb, goalWeight);
        AccountFieldValidator.validateWeeklyGoal(goalsOptionsMap.keySet(), weeklyGoal);
        AccountFieldValidator.validateEmail(email);
        AccountFieldValidator.validatePassword(password);
        AccountFieldValidator.validateUsername(username);

        this.firstName = firstName;
        this.goalsOptionsMap = Map.copyOf(goalsOptionsMap);
        this.activityLevel = activityLevel;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.countryOfOrigin = countryOfOrigin;
        this.heightFt = heightFt;
        this.heightIn = heightIn;
        this.weightLb = weightLb;
        this.goalWeight = goalWeight;
        this.weeklyGoal = weeklyGoal;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Account(String firstName, Map<Goal, Set<GoalOptions>> goalsOptionsMap, ActivityLevel activityLevel, Sex sex, Date dateOfBirth, String countryOfOrigin, float heightFt, float heightIn, float weightLb, float goalWeight, String email, String password, String username) {
        this(firstName, goalsOptionsMap, activityLevel, sex, dateOfBirth, countryOfOrigin, heightFt, heightIn, weightLb, goalWeight, null, email, password, username);
    }
}
