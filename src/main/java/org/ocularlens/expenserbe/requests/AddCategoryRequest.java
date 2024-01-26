package org.ocularlens.expenserbe.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.ocularlens.expenserbe.common.TransactionType;
import org.ocularlens.expenserbe.custom.validation.ValueOfEnum;

public record AddCategoryRequest(
        @NotNull(message = "type is required")
        @ValueOfEnum(enumClass = TransactionType.class, message = "type must be any of [CREDIT,DEBIT]")
        String type,
        @NotNull(message = "categoryName is required") @Size(min = 6, message = "categoryName should atleast be 6 characters long")
        String categoryName
) {
}
