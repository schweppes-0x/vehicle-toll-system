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
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final TollService tollService;

    public Optional<Vehicle> getVehicleByLicenseNumber(String licensePlateNumber) {
        return vehicleRepository.findById(licensePlateNumber);
    }

    public Vehicle register(VehicleDto vehicleDto) {
        if (getVehicleByLicenseNumber(vehicleDto.licensePlateNumber()).isPresent()) {
            throw VehicleException.alreadyExists(vehicleDto.licensePlateNumber());
        }

        Vehicle builtVehicle = Vehicle.builder()
                .licensePlateNumber(vehicleDto.licensePlateNumber())
                .manufactureDate(vehicleDto.manufactureDate())
                .build();
        try{
            Vehicle registeredVehicle = vehicleRepository.save(builtVehicle);
            tollService.purchaseToll(vehicleDto.licensePlateNumber(), vehicleDto.duration());
            return registeredVehicle;
        }catch (Exception e){
            throw VehicleException.invalidTollData();
        }
    }

}
