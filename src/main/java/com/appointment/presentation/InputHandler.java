package com.appointment.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Handles user input from the console.
 * 
 * @author awwadaa
 * @version 1.0
 */
public class InputHandler {

    private Scanner scanner;

    /**
     * Constructs an InputHandler object.
     */
    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads an integer choice from the user.
     * 
     * @return the entered integer
     */
    public int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    /**
     * Reads a string from the user.
     * 
     * @return the entered string
     */
    public String readString() {
        return scanner.nextLine();
    }

    /**
     * Reads a LocalDate from the user in yyyy-mm-dd format.
     * 
     * @return the entered date
     */
    public LocalDate readDate() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.print("Invalid date format. Enter date as yyyy-mm-dd: ");
            }
        }
    }

    /**
     * Reads a LocalTime from the user in HH:mm format.
     * 
     * @return the entered time
     */
    public LocalTime readTime() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalTime.parse(input);
            } catch (Exception e) {
                System.out.print("Invalid time format. Enter time as HH:mm: ");
            }
        }
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}