package com.makechi.connections;

import com.makechi.main.Player;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

public class Connections {

    public Connection connection;
    public Statement statement;

    public Connections() {

        var properties = new Properties();

        try (Reader in = Files.newBufferedReader(Path.of("database.properties"), StandardCharsets.UTF_8)) {

            properties.load(in);
            String drivers = properties.getProperty("jdbc.drivers");
            if (drivers != null) System.setProperty("jdbc.drivers", "drivers");
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPlayer(String name) {
        String query = "SELECT * FROM players WHERE username='" + name + "'";
        try {
            return (statement.executeQuery(query)).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerPlayer(String name) {
        int score = 0;
        String query = "INSERT INTO players(username, score) VALUES('" + name + "'," + score + ")";
        try {
            int result = statement.executeUpdate(query);
            if(result > 0) System.out.println("Player registered successfully!");
            else System.out.println("An error occurred during registration");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getPlayer(String name) {
        String query = "SELECT * FROM players WHERE username='" + name + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                return new Player(username, score);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Player("player",  0);
    }

    public void saveGame(Player player) {
        String username = player.getUsername();
        int score = player.getScore();

        String query = "UPDATE players SET score = " + score + " WHERE username = '" + username + "'";
        try {
            int result = statement.executeUpdate(query);
            if (result > 0) System.out.println("Game saved successfully");
            else System.out.println("An error occurred during saving");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
