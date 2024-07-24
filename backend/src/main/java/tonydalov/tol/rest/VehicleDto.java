package tonydalov.tol.rest;

import java.time.LocalDate;

public record VehicleDto(String licensePlateNumber,LocalDate manufactureDate) {
}
