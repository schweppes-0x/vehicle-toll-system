package tonydalov.tol.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Toll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private LocalDateTime tollExpiration;
    private TollDuration duration;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "license_plate_number", nullable = false)
    @JsonBackReference
    private Vehicle vehicle;

    @JsonIgnore
    public boolean isValid(){
        return tollExpiration != null && tollExpiration.isAfter(LocalDateTime.now());
    }

    @JsonGetter("licensePlate")
    public String getVehicleLicensePlate() {
        return vehicle != null ? vehicle.getLicensePlateNumber() : null;
    }
}
