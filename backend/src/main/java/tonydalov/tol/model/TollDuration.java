package tonydalov.tol.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum TollDuration {
    WEEK(BigDecimal.valueOf(10L)),
    MONTH(BigDecimal.valueOf(30L)),
    YEAR(BigDecimal.valueOf(100L)),;

    private final BigDecimal price;

    TollDuration(BigDecimal price){
        this.price = price;
    }
}
