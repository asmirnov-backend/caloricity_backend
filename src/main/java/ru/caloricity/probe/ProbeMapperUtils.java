package ru.caloricity.probe;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Named("ProbeMapperUtils")
@Component
@RequiredArgsConstructor
public class ProbeMapperUtils {
    private final ProbeService probeService;

    @Named("getExistingReferenceByIdOrThrow")
    public Probe getExistingReferenceByIdOrThrow(UUID probeId) {
        return probeService.getExistingReferenceByIdOrThrow(probeId);
    }
}
