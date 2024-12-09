package web.automation.fitnessPal.objects.account.enums;

public class Goals {
    public enum Goal implements Options {
        LOSE_WEIGHT(LoseWeight.class, "lose-weight"),
        MAINTAIN_WEIGHT(MaintainWeight.class, "maintain-weight"),
        GAIN_WEIGHT(GainWeight.class, "gain-weight"),
        GAIN_MUSCLE(GainMuscle.class, "gain-muscle", false),
        MODIFY_MY_DIET(ModifyDiet.class, "modify-diet"),
        MANAGE_STRESS(ManageStress.class, "manage-stress"),
        INCREASE_STEP_COUNT(IncreaseSteps.class, "increase-steps", false);

        public final Class<? extends Enum<?>> optionsClass;
        public final String relUrl;
        public final boolean hasMultipleOptions;

        Goal(Class<? extends Enum<?>> goalOptionsClass, String relUrl, boolean hasMultipleOptions) {
            if (goalOptionsClass.isAssignableFrom(GoalOptions.class))
                throw new RuntimeException(String.format("class %s does not implement class GoalOptions.", goalOptionsClass.getSimpleName()));

            this.optionsClass = goalOptionsClass;
            this.relUrl = relUrl;
            this.hasMultipleOptions = hasMultipleOptions;
        }

        Goal(Class<? extends Enum<?>> goalOptionsClass, String relUrl) {
            this(goalOptionsClass, relUrl, true);
        }

        public boolean isWeightGoal() {
            return this == LOSE_WEIGHT || this == MAINTAIN_WEIGHT || this == GAIN_WEIGHT;
        }
    }

    public enum LoseWeight implements GoalOptions {
        LACK_OF_TIME,
        REGIMEN_HARD,
        DIET_LACKS_VARIETY,
        FOOD_CHOICE_STRESS,
        HOLIDAYS_VACATION_EVENTS,
        FOOD_CRAVINGS,
        LACK_OF_PROGRESS
    }

    public enum LoseWeeklyGoal implements WeeklyGoal {
        HALF_POUND,
        ONE_POUND,
        ONE_AND_HALF_POUND,
        TWO_POUND
    }

    public enum MaintainWeight implements GoalOptions {
        LACK_OF_TIME,
        REGIMEN_HARD,
        DIET_LACKS_VARIETY,
        FOOD_CHOICE_STRESS,
        HOLIDAYS_VACATION_EVENTS,
        FOOD_CRAVINGS,
        LACK_OF_PROGRESS,
        DOESNT_TASTE_GOOD,
        TOO_EXPENSIVE,
        COOKING_HARD,
        NO_BARRIERS
    }

    public enum GainWeight implements GoalOptions {
        COMPETITIVE_SPORT,
        MUSCLE_FOR_FITNESS,
        UNDERWEIGHT,
        HEALTHCARE_PROVIDER,
        OTHER
    }

    public enum GainWeeklyGoal implements WeeklyGoal {
        HALF_POUND,
        ONE_POUND
    }

    public enum GainMuscle implements GoalOptions {
        TONE_UP,
        BULK_UP,
        GET_STRONG
    }

    public enum ModifyDiet implements GoalOptions {
        TRACK_MACROS,
        VEGAN,
        VEGETARIAN,
        PESCETARIAN,
        LESS_SUGAR,
        LESS_PROTEIN,
        LESS_DAIRY,
        LESS_CARBS,
        LESS_FAT,
        MORE_PROTEIN,
        MORE_FAT,
        MORE_FRUITS_AND_VEGETABLES,
        OTHER
    }

    public enum ManageStress implements GoalOptions {
        WALK,
        RUN,
        STRENGTH,
        BIKE,
        YOGA,
        STRETCH,
        MOTIVATIONAL_MEDIA,
        MEDITATE,
        MUSIC,
        OTHER,
        NOTHING_HELPS
    }

    public enum IncreaseSteps implements GoalOptions {
        //Unable to use numbers at the start of the enum, so used roman numerals instead
        /*
         *   M    = 1000
         *   MMM  = 3000
         *   VMM  = 7000
         */
        M_OR_LESS,
        M_TO_MMM,
        MMM_TO_VMM,
        MORE_THAN_VMM,
        DONT_KNOW
    }
}
