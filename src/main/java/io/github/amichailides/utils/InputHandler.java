package io.github.amichailides.utils;

import io.github.amichailides.view.StudentPrinter;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;


public class InputHandler {
    private final Scanner scanner;
    private final StudentPrinter printer;

    public InputHandler(Scanner scanner, StudentPrinter printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

    public String readValidated (String prompt,
                                 Function<String, String> sanitizer,
                                 Function<String, Optional<String>> validator,
                                 String errorMessage) {
        while (true){
            printer.printPrompt(prompt);
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
            printer.printPrompt(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine()); // Διαβάζουμε ως String για ασφάλεια

            Optional<Long> result = validator.apply(sanitized);

            if(result.isPresent()){
                return result.get();
            }
            printer.printError(errorMessage);
        }
    }

    public String readString(String prompt,
                             Function<String, String> sanitizer,
                             Function<String, Optional<String>> validator,
                             String errorMessage) {
        while (true) {
            printer.printPrompt(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine());
            Optional<String> result = validator.apply(sanitized);

            if (result.isPresent()){
                return result.get();
            }

            printer.printError(errorMessage);
        }
    }

    public int readInt(String prompt,
                       Function<String, String> sanitizer,
                       Function<String, Optional<Integer>> validator,
                       String errorMessage) {
        while (true) {
            printer.printPrompt(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine());
            Optional<Integer> result = validator.apply(sanitized);
            if (result.isPresent()){
                return result.get();
            }

            printer.printError(errorMessage);
        }
    }

    public boolean readBoolean(String prompt,
                               Function<String, String> sanitizer,
                               Function<String, Optional<Boolean>> validator,
                               String errorMessage) {
        while (true) {
            printer.printPrompt(prompt);
            String sanitized = sanitizer.apply(scanner.nextLine());
            Optional<Boolean> result = validator.apply(sanitized);

            if (result.isPresent()) {
                return result.get();
            }

            printer.printError(errorMessage);
        }

    }

}
