package tonydalov.tol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tonydalov.tol.exception.VehicleException;
import tonydalov.tol.model.Toll;
import tonydalov.tol.model.TollDuration;
import tonydalov.tol.model.Vehicle;
import tonydalov.tol.repository.VehicleRepository;
import tonydalov.tol.rest.TollResponse;
import tonydalov.tol.rest.VehicleDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  CarService {
    private final VehicleRepository vehicleRepository;
    private final TollService tollService;

    public Optional<Vehicle> getVehicleByLicenseNumber(String licensePlateNumber) {
        return vehicleRepository.findById(licensePlateNumber);
    }

    public Toll purchaseToll(String licensePlateNumber, TollDuration duration) {
        Optional<Vehicle> foundVehicle = getVehicleByLicenseNumber(licensePlateNumber);
        if(foundVehicle.isEmpty()){
            throw VehicleException.unknownLicense();
        }

        return tollService.purchaseToll(foundVehicle.get(), duration);
    }

    public TollResponse getValidity(String licensePlateNumber) {
        Vehicle found = getVehicleByLicenseNumber(licensePlateNumber)
                .orElseThrow(VehicleException::unknownLicense);

        // Check if vehicle has a valid toll
        Optional<Toll> latestToll = found.getLatestTol();
        boolean hasValidToll = found.hasValidToll();

        if (hasValidToll && latestToll.isPresent()) {
            return new TollResponse(licensePlateNumber, latestToll.get().getTollExpiration(), true);
        }

        return new TollResponse(licensePlateNumber,
                latestToll.map(Toll::getTollExpiration).orElse(null),
                hasValidToll);
    }


    public Vehicle register(VehicleDto vehicle){
        if(getVehicleByLicenseNumber(vehicle.licensePlateNumber()).isPresent()){
            throw VehicleException.alreadyExists(vehicle.licensePlateNumber());
        }

        Vehicle built = Vehicle.builder()
                .licensePlateNumber(vehicle.licensePlateNumber())
                .manufactureDate(vehicle.manufactureDate())
                .build();

        Toll initialToll = tollService.purchaseToll(built, vehicle.duration());
        built.setTollHistory(List.of(initialToll));

        return vehicleRepository.save(built);
    }
}
