package ru.caloricity.probe;

import java.util.Date;
import java.util.UUID;

public record ProbeInPageDto(UUID id, Date createdAt, Date updatedAt,
                             String name, ProbeType type,
                             String code) {}
