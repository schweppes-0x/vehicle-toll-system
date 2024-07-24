package tonydalov.tol.rest;

import java.time.LocalDateTime;

public record TollResponse(String license, LocalDateTime validUntil, boolean isValid) {
}
