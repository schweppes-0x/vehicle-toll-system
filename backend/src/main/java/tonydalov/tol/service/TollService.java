package tonydalov.tol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tonydalov.tol.exception.VehicleException;
import tonydalov.tol.model.Toll;
import tonydalov.tol.model.Vehicle;
import tonydalov.tol.model.TollDuration;
import tonydalov.tol.repository.TollRepository;
import tonydalov.tol.repository.VehicleRepository;
import tonydalov.tol.rest.TollResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TollService {

    private final TollRepository tollRepository;
    private final VehicleRepository vehicleRepository;

    public Toll purchaseToll(String licensePlate, TollDuration duration) throws VehicleException {
        if (licensePlate == null || !vehicleRepository.existsById(licensePlate)) {
            throw VehicleException.unknownLicense();
        }

        Vehicle foundVehicle = vehicleRepository.findById(licensePlate).orElseThrow();

        LocalDateTime currentExpiration = foundVehicle.hasValidToll()
                ? foundVehicle.getLatestTol().get().getTollExpiration()
                : LocalDateTime.now();

        Toll toll = Toll.builder()
                .vehicle(foundVehicle)
                .tollExpiration(getExpiration(currentExpiration, duration))
                .duration(duration)
                .price(duration.getPrice())
                .build();

        return tollRepository.save(toll);
    }

    public TollResponse getValidity(String licensePlateNumber) {
        Vehicle found = vehicleRepository.findById(licensePlateNumber)
                .orElseThrow(VehicleException::unknownLicense);

        Optional<Toll> latestToll = found.getLatestTol();
        boolean hasValidToll = found.hasValidToll();

        if (hasValidToll && latestToll.isPresent()) {
            return new TollResponse(licensePlateNumber, latestToll.get().getTollExpiration(), true);
        }

        return new TollResponse(licensePlateNumber,
                latestToll.map(Toll::getTollExpiration).orElse(null),
                hasValidToll);
    }

    private LocalDateTime getExpiration(LocalDateTime currentExpiration, TollDuration duration) {
        return switch (duration) {
            case YEAR -> currentExpiration.plusYears(1);
            case MONTH -> currentExpiration.plusMonths(1);
            case WEEK -> currentExpiration.plusWeeks(1);
        };
    }
}
