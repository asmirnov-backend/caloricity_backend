package ru.caloricity.probe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchCreateDto;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchDto;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchMapper;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearchCreateDto;
import ru.caloricity.probe.research.fatsresearch.FatsResearchDto;
import ru.caloricity.probe.research.fatsresearch.FatsResearchMapper;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchCreateDto;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchDto;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProbeMapperUnitTests {

    @Mock
    private DrySubstancesResearchMapper drySubstancesResearchMapper;
    @Mock
    private ProteinsResearchMapper proteinsResearchMapper;
    @Mock
    private FatsResearchMapper fatsResearchMapper;
    @InjectMocks
    private ProbeMapper probeMapper;

    @Test
    public void testToEntity() {
        ProbeCreateDto probeCreateDto = new ProbeCreateDto("name", ProbeType.FIRST, "code", 1.0, 2.0, 3.0,
                mock(FatsResearchCreateDto.class), mock(DrySubstancesResearchCreateDto.class),mock(ProteinsResearchCreateDto.class));
        when(fatsResearchMapper.toEntity(any(FatsResearchCreateDto.class))).thenReturn(new FatsResearch());
        when(proteinsResearchMapper.toEntity(any(ProteinsResearchCreateDto.class))).thenReturn(new ProteinsResearch());
        when(drySubstancesResearchMapper.toEntity(any(DrySubstancesResearchCreateDto.class))).thenReturn(new DrySubstancesResearch());

        Probe result = probeMapper.toEntity(probeCreateDto);

        assertEquals(probeCreateDto.name(), result.getName());
        assertEquals(probeCreateDto.type(), result.getType());
        assertEquals(probeCreateDto.code(), result.getCode());
        assertEquals(probeCreateDto.massTheory(), result.getMassTheory());
        assertEquals(probeCreateDto.bankaEmptyMass(), result.getBankaEmptyMass());
        assertEquals(probeCreateDto.bankaWithProbeMass(), result.getBankaWithProbeMass());
        verify(fatsResearchMapper).toEntity(probeCreateDto.fatsResearch());
        verify(proteinsResearchMapper).toEntity(probeCreateDto.proteinsResearchCreateDto());
        verify(drySubstancesResearchMapper).toEntity(probeCreateDto.drySubstancesResearch());
    }

    @Test
    public void testToDto() {
        Probe probe = Probe.builder()
                .name("name")
                .type(ProbeType.FIRST)
                .code("code")
                .massTheory(1.0)
                .bankaEmptyMass(2.0)
                .bankaWithProbeMass(3.0)
                .fatsResearch(new FatsResearch())
                .proteinsResearch(new ProteinsResearch())
                .drySubstancesResearch(new DrySubstancesResearch())
                .build();
        when(fatsResearchMapper.toDto(any(FatsResearch.class))).thenReturn(mock(FatsResearchDto.class));
        when(proteinsResearchMapper.toDto(any(ProteinsResearch.class))).thenReturn(mock(ProteinsResearchDto.class));
        when(drySubstancesResearchMapper.toDto(any(DrySubstancesResearch.class))).thenReturn(mock(DrySubstancesResearchDto.class));

        ProbeDto result = probeMapper.toDto(probe);

        assertEquals(probe.getId(), result.id());
        assertEquals(probe.getCreatedAt(), result.createdAt());
        assertEquals(probe.getUpdatedAt(), result.updatedAt());
        assertEquals(probe.getName(), result.name());
        assertEquals(probe.getType(), result.type());
        assertEquals(probe.getCode(), result.code());
        assertEquals(probe.getMassTheory(), result.massTheory());
        assertEquals(probe.getBankaEmptyMass(), result.bankaEmptyMass());
        assertEquals(probe.getBankaWithProbeMass(), result.bankaWithProbeMass());
        assertEquals(probe.getMassFact(), result.massFact());
        assertEquals(probe.getMinerals(), result.minerals());
        assertEquals(probe.getTheoreticalCaloricity(), result.theoreticalCaloricity());
        verify(fatsResearchMapper).toDto(probe.getFatsResearch());
        verify(proteinsResearchMapper).toDto(probe.getProteinsResearch());
        verify(drySubstancesResearchMapper).toDto(probe.getDrySubstancesResearch());
    }

    @Test
    public void testUpdateEntity() {
        Probe probe = Probe.builder()
                .name("name")
                .type(ProbeType.FIRST)
                .code("code")
                .massTheory(1.0)
                .bankaEmptyMass(2.0)
                .bankaWithProbeMass(3.0)
                .fatsResearch(new FatsResearch())
                .proteinsResearch(new ProteinsResearch())
                .drySubstancesResearch(new DrySubstancesResearch())
                .build();
        ProbeUpdateDto probeUpdateDto = new ProbeUpdateDto("updatedName", "updatedCode", 10.0, 20.0, 30.0,
                mock(FatsResearchCreateDto.class), mock(DrySubstancesResearchCreateDto.class),mock(ProteinsResearchCreateDto.class));
        when(fatsResearchMapper.toEntity(any(FatsResearchCreateDto.class))).thenReturn(new FatsResearch());
        when(proteinsResearchMapper.toEntity(any(ProteinsResearchCreateDto.class))).thenReturn(new ProteinsResearch());
        when(drySubstancesResearchMapper.toEntity(any(DrySubstancesResearchCreateDto.class))).thenReturn(new DrySubstancesResearch());

        probeMapper.updateEntity(probe, probeUpdateDto);

        assertEquals(probeUpdateDto.name(), probe.getName());
        assertEquals(probeUpdateDto.code(), probe.getCode());
        assertEquals(probeUpdateDto.massTheory(), probe.getMassTheory());
        assertEquals(probeUpdateDto.bankaEmptyMass(), probe.getBankaEmptyMass());
        assertEquals(probeUpdateDto.bankaWithProbeMass(), probe.getBankaWithProbeMass());
        assertNotNull(probeUpdateDto.fatsResearch());
        verify(fatsResearchMapper).updateEntity(probe.getFatsResearch(), probeUpdateDto.fatsResearch());
        assertNotNull(probeUpdateDto.proteinsResearch());
        verify(proteinsResearchMapper).updateEntity(probe.getProteinsResearch(), probeUpdateDto.proteinsResearch());
        assertNotNull(probeUpdateDto.drySubstancesResearch());
        verify(drySubstancesResearchMapper).updateEntity(probe.getDrySubstancesResearch(), probeUpdateDto.drySubstancesResearch());
    }
}