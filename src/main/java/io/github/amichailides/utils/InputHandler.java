package io.github.amichailides.utils;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readValidated (String prompt,
                                 Function<String, String> sanitizer,
                                 Predicate<String> validator,
                                 String errorMessage) {
        while (true){
            System.out.print(prompt);
            String rawInput = scanner.nextLine();
            String sanitized = sanitizer.apply(rawInput);

            if (validator.test(sanitized)) {
                return sanitized;
            }
            System.err.println(errorMessage);
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine(); // Διαβάζουμε ως String για ασφάλεια
            try {
                long value = Long.parseLong(input);
                if (value > 0) {
                    return value;
                }
                System.err.println("Σφαλμα: To ID πρεπει να ειναι θετικος αριθμος.");
            } catch (NumberFormatException e) {
                System.err.println("Σφαλμα: Παρακαλώ εισάγετε έναν έγκυρο αριθμό.");
            }
        }
    }

}
