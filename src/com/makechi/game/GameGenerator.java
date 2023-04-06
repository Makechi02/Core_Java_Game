package com.makechi.game;

import com.makechi.constants.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameGenerator {

    private int score;
    private final ArrayList<Question> questions = readQuestionsFromFile();
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
            Collections.shuffle(choices, new Random());
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ") " + choices.get(j));
            }
            System.out.print("Enter your choice: ");
            int userChoice = scanner.nextInt();
            String userAnswer = choices.get(userChoice - 1);
            if (Objects.equals(userAnswer, question.correctChoice())) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
                System.out.println("The correct answer is: " + question.correctChoice());
            }
        }
    }

    private static ArrayList<Question> readQuestionsFromFile() {
        ArrayList<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String questionText = parts[0];
                ArrayList<String> choices = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length - 1));
                int correctAnswer = Integer.parseInt(parts[parts.length - 1]);
                String choice = choices.get(correctAnswer - 1);
                questions.add(new Question(questionText, choices, choice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
}
