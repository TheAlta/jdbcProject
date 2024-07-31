package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserService {
    public void signUp(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();

        if (!Pattern.matches("[a-zA-Z]+", firstName) || !Pattern.matches("[a-zA-Z]+", lastName)) {
            throw new IllegalArgumentException("Name must contain only letters.");
        }
        if (!Pattern.matches("09\\d{9}", phoneNumber)) {
            throw new IllegalArgumentException("Phone number must be 11 digits and start with 09.");
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, phone_number) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.executeUpdate();
            System.out.println("User signed up successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean logIn(Scanner scanner) {
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();
        User user = getUserByPhoneNumber(phoneNumber);
        if (user != null) {
            System.out.println("Logged in successfully.");
            return true;
        } else {
            System.out.println("User not found.");
            return false;
        }
    }

    private User getUserByPhoneNumber(String phoneNumber) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE phone_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                return new User(firstName, lastName, phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
