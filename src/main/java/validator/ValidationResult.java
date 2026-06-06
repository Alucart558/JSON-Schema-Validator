package validator;

import java.util.List;

/**
 * Przechowuje wynik walidacji węzła JSON.
 * Wykorzystuje rekordy (Java 14+) do zwięzłej reprezentacji niemutowalnych danych.
 */
public record ValidationResult(boolean isValid, List<String> errors) {

    public static ValidationResult success() {
        return new ValidationResult(true, List.of());
    }

    public static ValidationResult failure(List<String> errors) {
        return new ValidationResult(false, List.copyOf(errors));
    }
}