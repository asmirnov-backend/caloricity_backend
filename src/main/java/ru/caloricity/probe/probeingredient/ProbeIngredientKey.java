package ru.caloricity.probe.probeingredient;
// see https://www.baeldung.com/jpa-many-to-many

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class ProbeIngredientKey implements Serializable {
    @Column(nullable = false)
    UUID probeId;

    @Column(nullable = false)
    UUID ingredientId;
}
