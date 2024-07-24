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
                .build();

        return tollRepository.save(toll);
    }

    private LocalDateTime getExpiration(LocalDateTime currentExpiration, TollDuration duration) {
        switch (duration) {
            case YEAR:
                return currentExpiration.plus(1, ChronoUnit.YEARS);
            case MONTH:
                return currentExpiration.plus(1, ChronoUnit.MONTHS);
            case WEEK:
                return currentExpiration.plus(1, ChronoUnit.WEEKS);
            default:
                throw new IllegalArgumentException("Invalid TollDuration: " + duration);
        }
    }
}
