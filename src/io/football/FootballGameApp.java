package io.football;

import java.util.Random;
import java.util.Scanner;

public class FootballGameApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("Welcome to the Enhanced OOP Football Game!");
        System.out.println("===========================================");

        System.out.print("Enter Team A's name: ");
        Team teamA = new Team(scanner.nextLine());
        System.out.print("Enter Team B's name: ");
        Team teamB = new Team(scanner.nextLine());
        System.out.println();

        Game game = new Game(teamA, teamB, scanner, new Random());
        game.start();

        scanner.close();
    }
}