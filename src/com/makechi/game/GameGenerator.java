package com.makechi.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameGenerator {

    private int score;
    ArrayList<Question> questions = readQuestionsFromFile();
    private final int totalQuestions = questions.size();

    public void getQuestion() {
        score = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Total questions: " + totalQuestions);
        Collections.shuffle(questions, new Random());

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.questionText());
            ArrayList<String> choices = question.choices();
//            Collections.shuffle(choices, new Random());
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ") " + choices.get(j));
            }
            System.out.print("Enter your choice: ");
            String userChoice = scanner.nextLine();
            if (Objects.equals(userChoice, question.correctChoice())) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    private static ArrayList<Question> readQuestionsFromFile() {
        ArrayList<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String questionText = parts[0];
                ArrayList<String> choices = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length - 1));
                String correctChoice = parts[parts.length - 1];
                questions.add(new Question(questionText, choices, correctChoice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}

record Question(String questionText, ArrayList<String> choices, String correctChoice) {}
