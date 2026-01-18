package io.github.amichailides.utils;

import io.github.amichailides.validation.common.IntegerSanitizer;

import java.util.Optional;
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
                                 Function<String, Optional<String>> validator,
                                 String errorMessage) {
        while (true){
            System.out.print(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine());

            Optional<String> result = validator.apply(sanitized);

            if (result.isPresent()) {
                return result.get();
            }
            System.err.println(errorMessage);
        }
    }

    //in case we'll need plain dumb readLong we'll play with method overloading readLong(Long long)
    public Long readLong(String prompt,
                         Function<String, String> sanitizer,
                         Function<String, Optional<Long>> validator,
                         String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine()); // Διαβάζουμε ως String για ασφάλεια

            Optional<Long> result = validator.apply(sanitized);

            if(result.isPresent()){
                return result.get();
            }
            System.err.println(errorMessage);
        }
    }

    public String readString(String prompt,
                             Function<String, String> sanitizer,
                             Function<String, Optional<String>> validator,
                             String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine());
            Optional<String> result = validator.apply(sanitized);

            if (result.isPresent()){
                return result.get();
            }

            System.err.println(errorMessage);
        }
    }

    public int readInt(String prompt,
                       Function<String, String> sanitizer,
                       Function<String, Optional<Integer>> validator,
                       String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine());
            Optional<Integer> result = validator.apply(sanitized);
            if (result.isPresent()){
                return result.get();
            }

            System.err.println(errorMessage);
        }
    }

}
