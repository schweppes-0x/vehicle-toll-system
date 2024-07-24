package tonydalov.tol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonydalov.tol.model.Toll;
import tonydalov.tol.model.Vehicle;

@Repository
public interface TollRepository extends JpaRepository<Toll, String> {
    Toll findByVehicle(Vehicle vehicle);
}
