package org.example;


import java.time.LocalDateTime;

public class Reservation {
    private String firstName;
    private String lastName;
    private LocalDateTime reservationTime;

    // Getters and Setters


    public Reservation() {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reservationTime = reservationTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", reservationTime=" + reservationTime +
                '}';
    }
}
