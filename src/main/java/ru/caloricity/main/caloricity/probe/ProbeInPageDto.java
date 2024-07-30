package ru.caloricity.main.caloricity.probe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ProbeInPageDto {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private ProbeType type;
    private String code;
    private float massTheory;
    private float massFact;
}