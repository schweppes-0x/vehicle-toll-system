package tonydalov.tol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @Column(nullable = false, unique = true)
    private String licensePlateNumber;

    @PastOrPresent
    private LocalDate manufactureDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Toll> tollHistory;

    @JsonIgnore
    public Optional<Toll> getLatestTol() {
        if (tollHistory != null && !tollHistory.isEmpty()) {
            return tollHistory.stream()
                    .max(Comparator.comparing(Toll::getTollExpiration));
        }
        return Optional.empty();
    }

    @JsonIgnore
    public boolean hasValidToll(){
        return this.getLatestTol().isPresent() && this.getLatestTol().get().isValid();
    }
}
