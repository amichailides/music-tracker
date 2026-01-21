package io.github.amichailides.validation;




public class ValidationConstants {
    private ValidationConstants() {}

    public static final String NAME_REGEX = "^[\\p{IsLatin}\\p{IsGreek} ]+$";
    public static final String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_REGEX = "^69\\d{8}$";
    public static final String ID_REGEX = "^[1-9][0-9]*$";

    // Error messages
    public static final String INVALID_FIRST_NAME = "Λάθος, το όνομα πρέπει να περιέχει μόνο γράμματα (χωρίς αριθμούς ή σύμβολα).";
    public static final String INVALID_LAST_NAME = "Λάθος, το όνομα πρέπει να περιέχει μόνο γράμματα (χωρίς αριθμούς ή σύμβολα).";
    public static final String INVALID_EMAIL = "Λάθος μορφή email. Πρέπει να είναι της μορφής user@example.com.";
    public static final String INVALID_PHONE = "Σφάλμα! Εισάγετε 10 ψηφία (π.χ. 6971234567). Διεθνή πρόσημα όπως +30 επιτρέπονται.";
    public static final String INVALID_LEVEL = "Μη έγκυρο επίπεδο. Παρακαλώ πληκτρολογήστε μία από τις διαθέσιμες επιλογές: BASIC, INTERMEDIATE, ADVANCED.";
    public static final String INVALID_ID = "Σφαλμα: Το ID πρεπει να ειναι θετικος αριθμος";
    public static final String INVALID_YES_NO_INPUT = "Παρακαλώ πατήστε 'y' για Ναι ή 'n' για Όχι.";
}
