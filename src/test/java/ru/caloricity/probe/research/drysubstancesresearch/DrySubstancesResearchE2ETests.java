package ru.caloricity.probe.research.drysubstancesresearch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.caloricity.common.exception.EntityNotFoundException;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.ProbeFactory;
import ru.caloricity.probe.ProbeRepository;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DrySubstancesResearchE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DrySubstancesResearchRepository repository;
    @Autowired
    private ProbeRepository probeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/dry-substances-researches/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        repository.save(new DrySubstancesResearchFactory().createSimple(probe));

        mvc.perform(get("/dry-substances-researches?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.content[0].byuksaParallelFirst").value(61))
                .andExpect(jsonPath("$.content[0].byuksaParallelSecond").value(62))
                .andExpect(jsonPath("$.content[0].byuksaAfterDryingParallelFirst").value(50))
                .andExpect(jsonPath("$.content[0].byuksaAfterDryingParallelSecond").value(52))
                .andExpect(jsonPath("$.content[0].massNaveskiParallelFirst").value(10))
                .andExpect(jsonPath("$.content[0].massNaveskiParallelSecond").value(10))
                .andExpect(jsonPath("$.content[0].dryResidueWeightParallelFirst").value(11))
                .andExpect(jsonPath("$.content[0].dryResidueWeightParallelSecond").value(10))
                .andExpect(jsonPath("$.content[0].dryResidueWeightAverage").value(10.5));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        DrySubstancesResearchCreateDto dto = DrySubstancesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(2.)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .probeId(probe.getId())
                .build();

        MvcResult result = mvc.perform(post("/dry-substances-researches")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        UUID id = UUID.fromString(jsonNode.get("id").asText());

        Optional<DrySubstancesResearch> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
        assertNotNull(createdEntity.get().getProbe());
        assertEquals(createdEntity.get().getProbe().getId(), probe.getId());
        assertEquals(createdEntity.get().getByuksaParallelFirst(), dto.byuksaParallelFirst());
        assertEquals(createdEntity.get().getByuksaParallelSecond(), dto.byuksaParallelSecond());
        assertEquals(createdEntity.get().getByuksaAfterDryingParallelFirst(), dto.byuksaAfterDryingParallelFirst());
        assertEquals(createdEntity.get().getByuksaAfterDryingParallelSecond(), dto.byuksaAfterDryingParallelSecond());
    }

    @Test
    void create_badRequest() throws Exception {
        DrySubstancesResearchCreateDto dto = DrySubstancesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(null)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .probeId(UUID.randomUUID())
                .build();

        mvc.perform(post("/dry-substances-researches")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        DrySubstancesResearch entity = repository.save(new DrySubstancesResearchFactory().createSimple());
        DrySubstancesResearchUpdateDto dto = new DrySubstancesResearchUpdateDto(2., 1., 1., 10.,10.,10.);

        mvc.perform(put("/dry-substances-researches/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<DrySubstancesResearch> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getByuksaParallelFirst(), dto.byuksaParallelFirst());
        assertEquals(updated.get().getByuksaParallelSecond(), dto.byuksaParallelSecond());
        assertEquals(updated.get().getByuksaAfterDryingParallelFirst(), dto.byuksaAfterDryingParallelFirst());
        assertEquals(updated.get().getByuksaAfterDryingParallelSecond(), dto.byuksaAfterDryingParallelSecond());
    }

    @Test
    void delete_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        DrySubstancesResearch entity = repository.save(new DrySubstancesResearchFactory().createSimple(probe));

        mvc.perform(delete("/dry-substances-researches/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<DrySubstancesResearch> deleted = repository.findById(entity.getId());
        assertTrue(deleted.isEmpty());
        assertTrue(probeRepository.findById(probe.getId()).isPresent());
    }
}