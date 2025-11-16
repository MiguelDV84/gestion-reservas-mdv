package com.example.reservasBoscoMdv.errors;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String error,
        String message
) {
}
