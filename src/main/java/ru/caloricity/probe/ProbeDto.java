package ru.caloricity.probe;

import java.util.Date;
import java.util.UUID;

public record ProbeDto(UUID id, Date createdAt, Date updatedAt, String name,
                       ProbeType type, String code, Float massTheory,
                       Float bankaEmptyMass, Float bankaWithProbeMass) {}
