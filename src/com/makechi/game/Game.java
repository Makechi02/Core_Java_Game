package com.makechi.game;

import com.makechi.connections.Connections;
import com.makechi.main.Player;

import java.util.Scanner;

public class Game {

    private Player player;
    GameGenerator gameGenerator = new GameGenerator();
    Connections connections = new Connections();


    public Game() {
        showCreator();
        getDetails();
        displayMenu();
    }

    private void showCreator() {
        String[] name = {"M", "A", "K", "E", "C", "H", "I"};
        try {
            System.out.print("\nCreated by:- ");
            Thread.sleep(500);
            for (String letter : name) {
                System.out.print(letter);
                Thread.sleep(500);
            }
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHi there, Please provide your details to continue.");
        System.out.print("\nEnter your username: ");
        String name = scanner.next();
        handleRegistration(name);
        System.out.println("Hello " + player.getUsername() + " welcome to the game");
    }

    private void handleRegistration(String name) {
        if (!connections.checkPlayer(name)) connections.registerPlayer(name);
        player = connections.getPlayer(name);
    }

    private void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n++++++++++++++++++++++++");
        System.out.println("+   Enter Your choice    +");
        System.out.println("++++++++++++++++++++++++++");
        System.out.println("+ 1.Play                 +");
        System.out.println("+ 2.Exit                 +");
        System.out.println("++++++++++++++++++++++++++");
        System.out.println();

        int choice = scanner.nextInt();
        processChoice(choice);
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> handlePlay();
            case 2 -> handleExit();
        }
    }

    private void handlePlay() {
        loading();
        
        System.out.println("\nFastest Finger Question");
        gameGenerator.getQuestion();
        player.setScore(gameGenerator.getScore());
        System.out.println("Final Score: " + player.getScore() + "/" + gameGenerator.getTotalQuestions());
        saveGame(player);
        System.out.println();
        displayMenu();
    }

    private void loading () {
        try {
            System.out.print("Loading");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGame(Player player) {
        connections.saveGame(player);
    }

    private void handleExit() {
        System.out.println("Bye " + player.getUsername() + ",");
        System.out.println("Hope to see you soon!");
        System.exit(0);
    }

}
