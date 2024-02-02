package org.ocularlens.expenserbe.custom.util;

public class StringExtractor {
    public static String extractValueFromErrorMessage(String errorMessage) {
        int startIndex = errorMessage.indexOf("'");
        int endIndex = errorMessage.indexOf("'", startIndex + 1);
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return errorMessage.substring(startIndex + 1, endIndex);
        }
        return null;
    }
}
