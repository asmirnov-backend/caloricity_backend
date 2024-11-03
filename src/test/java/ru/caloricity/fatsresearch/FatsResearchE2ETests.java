package ru.caloricity.fatsresearch;

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
class FatsResearchE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FatsResearchRepository repository;
    @Autowired
    private ProbeRepository probeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/fats-researches/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        repository.save(new FatsResearchFactory().createSimple(probe));

        mvc.perform(get("/fats-researches?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.content[0].patronMassBeforeExtractionParallelFirst").value(80))
                .andExpect(jsonPath("$.content[0].patronMassAfterExtractionParallelFirst").value(11))
                .andExpect(jsonPath("$.content[0].patronMassBeforeExtractionParallelSecond").value(90))
                .andExpect(jsonPath("$.content[0].patronMassAfterExtractionParallelSecond").value(12))
                .andExpect(jsonPath("$.content[0].dryResidueWeightParallelFirst").value(69))
                .andExpect(jsonPath("$.content[0].dryResidueWeightParallelSecond").value(78))
                .andExpect(jsonPath("$.content[0].dryResidueWeightAverage").value(73.5));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        FatsResearchCreateDto dto = FatsResearchCreateDto.builder()
                .patronMassBeforeExtractionParallelFirst(1.)
                .patronMassBeforeExtractionParallelSecond(2.)
                .patronMassAfterExtractionParallelFirst(1.)
                .patronMassAfterExtractionParallelSecond(2.)
                .probeId(probe.getId())
                .build();

        MvcResult result = mvc.perform(post("/fats-researches")
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

        Optional<FatsResearch> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
        assertNotNull(createdEntity.get().getProbe());
        assertEquals(createdEntity.get().getProbe().getId(), probe.getId());
    }

    @Test
    void create_badRequest() throws Exception {
        FatsResearchCreateDto dto = FatsResearchCreateDto.builder()
                .patronMassBeforeExtractionParallelFirst(1.)
                .patronMassBeforeExtractionParallelSecond(null)
                .patronMassAfterExtractionParallelFirst(1.)
                .patronMassAfterExtractionParallelSecond(2.)
                .probeId(UUID.randomUUID())
                .build();

        mvc.perform(post("/fats-researches")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        FatsResearch entity = repository.save(new FatsResearchFactory().createSimple());
        FatsResearchUpdateDto dto = FatsResearchUpdateDto.builder()
                .patronMassBeforeExtractionParallelFirst(2.)
                .patronMassBeforeExtractionParallelSecond(2.)
                .patronMassAfterExtractionParallelFirst(1.)
                .patronMassAfterExtractionParallelSecond(2.)
                .build();

        mvc.perform(put("/fats-researches/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<FatsResearch> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getPatronMassBeforeExtractionParallelFirst(), dto.patronMassBeforeExtractionParallelFirst());
    }

    @Test
    void delete_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        FatsResearch entity = repository.save(new FatsResearchFactory().createSimple(probe));

        mvc.perform(delete("/fats-researches/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<FatsResearch> deleted = repository.findById(entity.getId());
        assertTrue(deleted.isEmpty());
        assertTrue(probeRepository.findById(probe.getId()).isPresent());
    }

}
