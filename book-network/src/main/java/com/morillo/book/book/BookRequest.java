package com.morillo.book.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(                              // Record       SE PUEDE USAR CLASE O RECORD
        Integer id,
        @NotNull(message = "100")                       // message SIGNIFICA AL CAMPO QUE GENERA EL ERROR DE VALIDACION
        @NotEmpty(message = "100")
        String title,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String authorName,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String isbn,                                    // International Standard Book Number
        @NotNull(message = "103")
        @NotEmpty(message = "103")
        String synopsis,
        boolean shareable
) {
}
