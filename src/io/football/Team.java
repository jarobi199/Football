package io.football;

public class Team {

    private String name;
    private int score;
    private int fieldPosition;

    public static final int INITIALIZE_FIELD_POSITION = 20;

    public Team(String name) {
        this.name = name;
        this.score = 0;
        this.fieldPosition = INITIALIZE_FIELD_POSITION;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getFieldPosition() {
        return fieldPosition;
    }

    public void updateFieldPosition(int yards) {
        this.fieldPosition = fieldPosition + yards;
    }

    public void resetFieldPosition() {
        this.fieldPosition = INITIALIZE_FIELD_POSITION;
    }

}