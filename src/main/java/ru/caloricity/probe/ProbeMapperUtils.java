package ru.caloricity.probe;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.UUID;

@Named("ProbeMapperUtils")
@Component
@RequiredArgsConstructor
public class ProbeMapperUtils {
    private final ProbeService probeService;

    @Named("mapProbeIdToEntity")
    public Probe mapProbeIdToEntity(UUID probeId) {
        return probeService.findById(probeId).orElseThrow(EntityNotFoundException::new);
    }
}
