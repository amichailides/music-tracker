package io.github.amichailides.validation.common;

public class MobileSanitizer {
    private MobileSanitizer () {};

    public static String clean (String input) {
        if (input == null || input.isBlank()) return "";
        // κραταω μονο αριθμους basically, σβηνω κανα + που βαζουν κατι τρελοι
        String cleaned = input.replaceAll("[^0-9]", "");

        // αν καποιος εβαλε +30
        if (cleaned.startsWith("30") && cleaned.length() == 12) {
            cleaned = cleaned.substring(2);
        }
        return cleaned;
    }
}
