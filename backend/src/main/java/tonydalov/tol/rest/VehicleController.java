package tonydalov.tol.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tonydalov.tol.exception.ApiException;
import tonydalov.tol.exception.VehicleException;
import tonydalov.tol.model.Toll;
import tonydalov.tol.model.TollDuration;
import tonydalov.tol.model.Vehicle;
import tonydalov.tol.service.CarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class VehicleController {
    private final CarService carService;

    @GetMapping("/{license}")
    public ResponseEntity<?> getValidity(@PathVariable String license) {
        try {
            TollResponse tollResponse = carService.getValidity(license);
            return ResponseEntity.ok(tollResponse);
        } catch (VehicleException | ApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> registerCar(@RequestBody VehicleDto vehicle) {
        try{
            Vehicle created = carService.register(vehicle);
            return ResponseEntity.ok(created);
        }catch (VehicleException | ApiException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{license}/toll")
    public ResponseEntity<?> purchaseToll(@PathVariable String license, @RequestParam TollDuration duration) {
        try {
            Toll purchased = carService.purchaseToll(license, duration);
            return ResponseEntity.ok(purchased);
        } catch (VehicleException | ApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
