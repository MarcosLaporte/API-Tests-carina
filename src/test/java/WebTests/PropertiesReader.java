package WebTests;

import web.automation.fitnessPal.objects.account.Account;
import web.automation.fitnessPal.objects.account.enums.ActivityLevel;
import web.automation.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.fitnessPal.objects.account.enums.Goals;
import web.automation.fitnessPal.objects.account.enums.Goals.Goal;
import web.automation.fitnessPal.objects.account.enums.WeeklyGoal;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class PropertiesReader {

    public static Account getAccount() throws ParseException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/account.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String firstName = properties.getProperty("firstName");
        Map<Goal, Set<GoalOptions>> goalsOptionsMap = loadMapFromProperty(properties.getProperty("goalsOptionsMap"));
        ActivityLevel activityLevel = ActivityLevel.valueOf(properties.getProperty("activityLevel"));
        Account.Sex sex = Account.Sex.valueOf(properties.getProperty("sex"));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date dateOfBirth = formatter.parse(properties.getProperty("dateOfBirth"));

        String countryOfOrigin = properties.getProperty("countryOfOrigin");
        float heightFt = Float.parseFloat(properties.getProperty("heightFt"));
        float heightIn = Float.parseFloat(properties.getProperty("heightIn"));
        float weightLb = Float.parseFloat(properties.getProperty("weightLb"));
        float goalWeight = Float.parseFloat(properties.getProperty("goalWeight"));

        String weeklyGoalStr = properties.getProperty("weeklyGoal");
        WeeklyGoal weeklyGoal = getWeeklyGoal(goalsOptionsMap.keySet(), weeklyGoalStr);

        String email = properties.getProperty("email");
        String password = properties.getProperty("password");
        String username = properties.getProperty("username");

        return new Account(firstName, goalsOptionsMap, activityLevel, sex, dateOfBirth, countryOfOrigin, heightFt, heightIn, weightLb, goalWeight, weeklyGoal, email, password, username);
    }

    private static Map<Goal, Set<GoalOptions>> loadMapFromProperty(String mapString) {
        Map<Goal, Set<GoalOptions>> map = new HashMap<>();

        if (mapString != null) {
            String[] keyValuePairs = mapString.split(";");
            for (String pair : keyValuePairs) {
                String[] parts = pair.split(":");
                Goal goalKey = Goal.valueOf(parts[0]);

                Set<GoalOptions> options = Arrays.stream(parts[1].split(","))
                        .map(value -> Enum.valueOf((Class<? extends Enum>) goalKey.optionsClass, value))
                        .map(GoalOptions.class::cast)
                        .collect(Collectors.toUnmodifiableSet());

                map.put(goalKey, options);
            }
        }

        return map;
    }

    private static WeeklyGoal getWeeklyGoal(Set<Goal> goals, String weeklyGoalStr) {
        Class<? extends WeeklyGoal> weeklyGoalClass;

        if (goals.contains(Goal.LOSE_WEIGHT))
            weeklyGoalClass = Goals.LoseWeeklyGoal.class;
        else if (goals.contains(Goal.GAIN_WEIGHT))
            weeklyGoalClass = Goals.GainWeeklyGoal.class;
        else return null;

        if (weeklyGoalStr != null && !weeklyGoalStr.isEmpty()) {
            return (WeeklyGoal) Enum.valueOf((Class<? extends Enum>) weeklyGoalClass, weeklyGoalStr);
        }

        throw new NullPointerException("Weekly goal was empty.");
    }
}
