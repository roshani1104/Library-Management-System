package com.library.util;

import com.library.exception.InvalidInputException;
import java.util.Scanner;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    // Prevent object creation
    private InputValidator() {}

    // Reads a positive integer only (no zero, no negatives)
    public static int readPositiveInt(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value <= 0) {
                    throw new InvalidInputException("❌ Invalid " + fieldName + ": must be greater than 0.");
                }
                return value;
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid " + fieldName + ": please enter a valid number.");
            }
        }
    }

    // Reads any integer (including 0, for menu choices)
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid input: please enter a valid number.");
            }
        }
    }

    // Reads a non-empty string
    public static String readNonEmptyString(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                String value = scanner.nextLine().trim();
                if (value.isEmpty()) {
                    throw new InvalidInputException("❌ Invalid " + fieldName + ": this field cannot be empty.");
                }
                return value;
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    // Reads an optional string (can be blank)
    public static String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}