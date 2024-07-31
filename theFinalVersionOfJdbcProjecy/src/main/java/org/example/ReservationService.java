package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ReservationService {
    public void menu(Scanner scanner) {
        while (true) {
            System.out.println("1. Make a Reservation");
            System.out.println("2. Cancel a Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Log Out");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    makeReservation(scanner);
                    break;
                case 2:
                    cancelReservation(scanner);
                    break;
                case 3:
                    viewReservations();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void makeReservation(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter reservation time (yyyy-MM-ddTHH:mm):");
        String time = scanner.nextLine();
        LocalDateTime reservationTime = LocalDateTime.parse(time);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO reservations (first_name, last_name, reservation_time) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setObject(3, reservationTime);
            statement.executeUpdate();
            System.out.println("Reservation made successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cancelReservation(Scanner scanner) {
        System.out.println("Enter reservation time to cancel (yyyy-MM-ddTHH:mm):");
        String time = scanner.nextLine();
        LocalDateTime reservationTime = LocalDateTime.parse(time);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM reservations WHERE reservation_time = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, reservationTime);
            statement.executeUpdate();
            System.out.println("Reservation cancelled successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewReservations() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM reservations ORDER BY reservation_time";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LocalDateTime reservationTime = resultSet.getObject("reservation_time", LocalDateTime.class);
                System.out.println(firstName + " " + lastName + " - " + reservationTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
