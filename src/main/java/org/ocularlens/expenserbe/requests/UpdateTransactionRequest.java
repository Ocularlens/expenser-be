package org.ocularlens.expenserbe.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTransactionRequest(
        @NotNull(message = "transactionDate is required")
        @Past(message = "transactionDate should be in the past")
        LocalDateTime transactionDate,
        @NotNull(message = "amount is required")
        @Min(value = 1, message = "minimum amount is 1")
        Double amount,
        @Size(min = 6, message = "notes should atleast be 6 characters long")
        String notes,
        @NotNull(message = "categoryId is required")
        Integer categoryId
) {
}
