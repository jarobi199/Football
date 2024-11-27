package io.football;

import java.util.Random;

public class ActionHandler {

    public int performAction(OffensiveAction action, DefensiveAction defense, Random random) {
        return switch (action) {
            case PASS -> attemptPass(defense, random);
            case RUN -> attemptRun(defense, random);
            case FIELD_GOAL -> attemptFieldGoal(random);
            case PUNT -> attemptPunt(random);
        };
    }

    public int attemptPass(DefensiveAction defense, Random random) {
        System.out.println("Attempting a pass...");
        if (defense == DefensiveAction.INTERCEPTION && random.nextInt(100) < 30) {
            System.out.println("The pass was intercepted! Turnover!");
            return -20; // Lose yards on interception
        }
        if (random.nextInt(100) < 60) {
            int yards = 10 + random.nextInt(15); // Gain 10-25 yards
            System.out.println("Pass successful! Gained " + yards + " yards.");
            return yards;
        } else {
            System.out.println("Pass failed.");
            return 0;
        }
    }

    public int attemptRun(DefensiveAction defense, Random random) {
        System.out.println("Attempting a run...");
        if (defense == DefensiveAction.BLOCK && random.nextInt(100) < 40) {
            System.out.println("The run was blocked! No yards gained.");
            return 0;
        }
        if (random.nextInt(100) < 50) {
            int yards = 5 + random.nextInt(10); // Gain 5-15 yards
            System.out.println("Run successful! Gained " + yards + " yards.");
            return yards;
        } else {
            System.out.println("Run stopped.");
            return 0;
        }
    }

    public int attemptFieldGoal(Random random) {
        System.out.println("Attempting a field goal...");
        if (random.nextInt(100) < 70) {
            System.out.println("Field goal successful! 3 points!");
            return 3;
        } else {
            System.out.println("Field goal missed.");
            return 0;
        }
    }

    public int attemptPunt(Random random) {
        System.out.println("Attempting a punt...");
        int yards = 30 + random.nextInt(20); // Gain 30-50 yards
        System.out.println("Punt successful! Gained " + yards + " yards of field position.");
        return yards;
    }
}
