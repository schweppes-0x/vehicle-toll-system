package tonydalov.tol.rest;

import tonydalov.tol.model.TollDuration;

import java.time.LocalDate;

public record VehicleDto(String licensePlateNumber, LocalDate manufactureDate, TollDuration duration) {
}
