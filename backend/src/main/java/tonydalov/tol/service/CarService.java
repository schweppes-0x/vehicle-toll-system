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
        Optional<Vehicle> foundVehicle = getVehicleByLicenseNumber(licensePlateNumber);
        if(foundVehicle.isEmpty()){
            throw VehicleException.unknownLicense();
        }else{
            if(foundVehicle.get().hasValidToll()){
                return new TollResponse(licensePlateNumber, foundVehicle.get().getLatestTol().get().getTollExpiration(), true);
            }
            Optional<Toll> lastExpiredToll = foundVehicle.get().getLatestTol();
            if(lastExpiredToll.isPresent()){
                return new TollResponse(licensePlateNumber, lastExpiredToll.get().getTollExpiration(), false);
            }
            else return new TollResponse(licensePlateNumber, null, false);
        }
    }

    public Vehicle register(VehicleDto vehicle){
        if(getVehicleByLicenseNumber(vehicle.licensePlateNumber()).isPresent()){
            throw VehicleException.alreadyExists(vehicle.licensePlateNumber());
        }

        return vehicleRepository.save(Vehicle.builder()
                .licensePlateNumber(vehicle.licensePlateNumber())
                .manufactureDate(vehicle.manufactureDate())
                .tollHistory(List.of())
                .build());
    }
}
