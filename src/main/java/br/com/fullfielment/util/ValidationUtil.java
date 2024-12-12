package br.com.fullfielment.util;

public class ValidationUtil {

    private ValidationUtil() {}

    public static boolean isValidPostalCode(String postalCode) {
        // Validação simples
        return postalCode != null && postalCode.matches("\\d{5}-\\d{3}");
    }
}
