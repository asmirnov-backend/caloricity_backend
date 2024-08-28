package ru.caloricity.probe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ProbeDto {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private ProbeType type;
    private String code;
    private Float massTheory;
    private Float bankaEmptyMass;
    private Float bankaWithProbeMass;
}