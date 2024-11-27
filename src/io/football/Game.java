package io.football;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Team teamA;
    private final Team teamB;
    private final Scanner scanner;
    private final Random random;
    private ActionHandler actionHandler = new ActionHandler();

    private static final int FIELD_LENGTH = 100;
    private static final int YARDS_FOR_FIRST_DOWN = 10;
    private static final int MAX_DOWNS = 4;

    public Game(Team teamA, Team teamB, Scanner scanner, Random random) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.scanner = scanner;
        this.random = random;
    }

    public void start() {
        System.out.println("Each team will take turns choosing an action.");
        System.out.println("Available actions: Pass, Run, Field Goal, Punt.");
        System.out.println("Advance to the opponent's end zone to score a touchdown!");
        System.out.println();

        int rounds = 5;
        for (int round = 1; round <= rounds; round++) {
            System.out.println("===== Round " + round + " =====");

            takeTurn(teamA, teamB);
            takeTurn(teamB, teamA);

            System.out.println("Score after Round " + round + ":");
            System.out.println(teamA.getName() + ": " + teamA.getScore() + " | " + teamB.getName() + ": " + teamB.getScore());
            System.out.println();
        }

        displayWinner();
    }

    private void takeTurn(Team activeTeam, Team opponentTeam) {
        System.out.println(activeTeam.getName() + "'s turn:");
        System.out.println("Current field position: " + activeTeam.getFieldPosition() + " yards.");
        
        int currentDown = 1;
        int yardsToGo = YARDS_FOR_FIRST_DOWN;
        boolean activePossession = true;

        while (activePossession && (currentDown <= MAX_DOWNS)) {
            System.out.println("Down: " + currentDown);
            System.out.println("Yards to go: " + yardsToGo);
            System.out.print("Choose an action (Pass, Run, Field Goal, Punt): ");
            String action = scanner.nextLine().trim().toLowerCase();

            // AI makes a defensive choice based on the action and field position
            String defense = chooseDefenseAI(action, activeTeam.getFieldPosition());

            int yardage = actionHandler.performAction(action, defense, random);

            if (action.equals("field goal") && activeTeam.getFieldPosition() >= 50) {
                System.out.println("Field goal attempt!");
                activeTeam.addScore(yardage);
                activePossession = false;
            } else if (action.equals("punt")) {
                System.out.println("Punting the ball...");
                opponentTeam.updateFieldPosition(-yardage);
                activePossession = false;
            } else {
                activeTeam.updateFieldPosition(yardage);
                yardsToGo -= yardage;

                if (activeTeam.getFieldPosition() >= FIELD_LENGTH) {
                    System.out.println("Touchdown! " + activeTeam.getName() + " scores 6 points!");
                    activeTeam.addScore(6);
                    activeTeam.resetFieldPosition();
                    opponentTeam.resetFieldPosition();
                    activePossession = false;
                }

                if (yardsToGo <= 0) {
                    System.out.println("First down achieved! Resetting downs and yards to go.");
                    currentDown = 1;
                    yardsToGo = YARDS_FOR_FIRST_DOWN;
                } else if (yardage < 0) {
                    System.out.println("Lost yardage!");
                } else {
                    currentDown++;
                }

                if (currentDown > MAX_DOWNS) {
                    System.out.println("Turnover on downs! " + opponentTeam.getName() + " gains possession.");
                    opponentTeam.resetFieldPosition();
                    activePossession = false;
                }

                System.out.println("Updated field position: " + activeTeam.getFieldPosition() + " yards.");
            }
        }
    }

    private void displayWinner() {
        System.out.println("===== Final Score =====");
        System.out.println(teamA.getName() + ": " + teamA.getScore() + " | " + teamB.getName() + ": " + teamB.getScore());

        if (teamA.getScore() > teamB.getScore()) {
            System.out.println(teamA.getName() + " wins!");
        } else if (teamB.getScore() > teamA.getScore()) {
            System.out.println(teamB.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public String chooseDefenseAI(String action, int fieldPosition) {
        // Defensive AI logic
        if (action.equals("pass")) {
            if (fieldPosition > 70) {
                // In red zone, more likely to defend pass
                return "interception";
            } else {
                return random.nextInt(100) < 50 ? "interception" : "none";
            }
        } else if (action.equals("run")) {
            if (fieldPosition < 30) {
                // Closer to own end zone, stronger defense against run
                return random.nextInt(100) < 60 ? "block" : "none";
            } else {
                return random.nextInt(100) < 40 ? "block" : "none";
            }
        } else {
            // Default to "none" for punt and field goal
            return "none";
        }
    }
}
