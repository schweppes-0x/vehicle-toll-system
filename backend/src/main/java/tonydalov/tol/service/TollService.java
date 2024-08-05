package tonydalov.tol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tonydalov.tol.exception.VehicleException;
import tonydalov.tol.model.Toll;
import tonydalov.tol.model.Vehicle;
import tonydalov.tol.model.TollDuration;
import tonydalov.tol.repository.TollRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TollService {

    private final TollRepository tollRepository;

    public Toll purchaseToll(Vehicle vehicle, TollDuration duration) throws VehicleException {
        LocalDateTime currentExpiration;
        if (vehicle.hasValidToll()) {
            currentExpiration = vehicle.getLatestTol().get().getTollExpiration();
        } else {
            currentExpiration = LocalDateTime.now();
        }

        Toll toll = Toll.builder()
                .vehicle(vehicle)
                .tollExpiration(getExpiration(currentExpiration, duration))
                .duration(duration)
                .price(duration.getPrice())
                .build();

        return tollRepository.save(toll);
    }

    private LocalDateTime getExpiration(LocalDateTime currentExpiration, TollDuration duration) {
        return switch (duration) {
            case YEAR -> currentExpiration.plusYears(1);
            case MONTH -> currentExpiration.plusMonths(1);
            case WEEK -> currentExpiration.plusWeeks(1);
        };
    }
}
