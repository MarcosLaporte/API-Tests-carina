package web.automation.fitnessPal;

import org.apache.commons.lang3.StringUtils;
import web.automation.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;
import web.automation.fitnessPal.objects.account.enums.WeeklyGoal;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class AccountFieldValidator {
    public static void validateFirstName(String name) throws IllegalArgumentException {
        if (StringUtils.isAllBlank(name) || name.length() > 30)
            throw new IllegalArgumentException("Name must be between 1 and 30 characters long.");
    }

    public static void validateGoalsAndOptions(Map<Goal, Set<GoalOptions>> goalListMap) throws IllegalArgumentException {
        validateGoals(goalListMap.keySet());

        for (Map.Entry<Goal, Set<GoalOptions>> goalListEntry : goalListMap.entrySet()) {
            Goal goal = goalListEntry.getKey();
            Set<GoalOptions> options = goalListEntry.getValue();
            validateGoalOptions(goal, options);
        }
    }

    public static void validateGoals(Set<Goal> goals) throws IllegalArgumentException {
        if (goals == null || goals.isEmpty() || goals.size() > 3) {
            throw new GoalsException("Must select between 1 and 3 goals.");
        }

        long weightGoalCount = goals.stream()
                .filter(Goal::isWeightGoal)
                .count();

        if (weightGoalCount > 1)
            throw new GoalsException("Can't have more than 1 weight goal.");
    }

    public static <T extends GoalOptions> void validateGoalOptions(Goal goal, Set<T> options) throws IllegalArgumentException {
        if (options == null || options.isEmpty())
            throw new GoalsException("Must select at least 1 option.");

        if (!goal.hasMultipleOptions && options.size() > 1)
            throw new GoalsException(goal + " goal only allows one option.");

        for (GoalOptions option : options) {
            if (goal.optionsClass != option.getClass())
                throw new GoalsException(option + " doesn't belong to " + goal + " goal.");
        }
    }

    public static class GoalsException extends IllegalArgumentException {
        public GoalsException(String message) {
            super(message);
        }
    }

    public static void validateAge(Date dob) {
        LocalDate localDob = new java.sql.Date(dob.getTime()).toLocalDate();
        Period age = Period.between(localDob, LocalDate.now());

        if (age.getYears() < 18)
            throw new IllegalArgumentException("Age must be greater than 18.");
    }

    public static void validateHeight(float heightFt, float heightIn) throws IllegalArgumentException {
        if (heightFt < 2 || (heightFt == 2 && heightIn <= 2)
                || heightFt > 7 || (heightFt == 7 && heightIn >= 11))
            throw new IllegalArgumentException("Height must be higher than 2'2\" and less than 7'11\".");
    }

    public static void validateWeight(float weightLb) throws IllegalArgumentException {
        if (weightLb < 1 || weightLb >= 1000)
            throw new IllegalArgumentException("Weight must be between 1 and 1000 lbs.");
    }

    public static void validateGoalWeight(Set<Goal> goals, float currentWeight, float goalWeight) throws IllegalArgumentException {
        if (goalWeight < 1)
            throw new IllegalArgumentException("Weight can't be less than 1.");

        if (goals.contains(Goal.LOSE_WEIGHT) && goalWeight >= currentWeight)
            throw new IllegalArgumentException("Goal weight must be lower than current weight if one of the goals is to lose weight.");
        else if (goals.contains(Goal.GAIN_WEIGHT) && goalWeight <= currentWeight)
            throw new IllegalArgumentException("Goal weight must be higher than current weight if one of the goals is to gain weight.");
    }

    public static void validateWeeklyGoal(Set<Goal> goals, WeeklyGoal weeklyGoal) throws IllegalArgumentException {
        if ((goals.contains(Goal.LOSE_WEIGHT) || goals.contains(Goal.GAIN_WEIGHT)) && weeklyGoal == null)
            throw new IllegalArgumentException("Must set a weekly goal for losing or gaining weight.");
    }

public static void validateEmail(String email) throws IllegalArgumentException {
    Pattern emailPattern = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    if (!emailPattern.matcher(email).matches())
        throw new IllegalArgumentException(email + " is not a valid email address.");
}

    public static void validatePassword(String password) throws IllegalArgumentException {
        if (password.length() < 10 || StringUtils.containsWhitespace(password))
            throw new IllegalArgumentException("Password must have at least 10 characters, no spaces.");
    }

    public static void validateUsername(String username) throws IllegalArgumentException {
        if (username.length() < 4 || username.length() > 30)
            throw new IllegalArgumentException("Username must be between 4 and 30 characters long.");

        Pattern usernamePattern = Pattern.compile("^[A-Za-z0-9]*");
        if (!usernamePattern.matcher(username).matches())
            throw new IllegalArgumentException(username + " is invalid. Username can't contain special characters.");
    }

}
