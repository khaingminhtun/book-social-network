package com.aizen.book.api.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(

        Integer id,

        @NotBlank(message = "100")
        String title,

        @NotBlank(message = "102")
        String authorName,

        @NotBlank(message = "103")
        String isbn,

        @NotBlank(message = "104")
        String synopsis,

        boolean shareable
) {}
