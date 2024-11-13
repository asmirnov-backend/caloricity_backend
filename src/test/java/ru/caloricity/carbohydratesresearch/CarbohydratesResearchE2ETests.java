package ru.caloricity.carbohydratesresearch;

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
class CarbohydratesResearchE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarbohydratesResearchRepository repository;
    @Autowired
    private ProbeRepository probeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/carbohydrates-researches/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        repository.save(new CarbohydratesResearchFactory().createSimple(probe));

        mvc.perform(get("/carbohydrates-researches?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.content[0].byuksaParallelFirst").value(60))
                .andExpect(jsonPath("$.content[0].byuksaParallelSecond").value(61))
                .andExpect(jsonPath("$.content[0].byuksaAfterDryingParallelFirst").value(50))
                .andExpect(jsonPath("$.content[0].byuksaAfterDryingParallelSecond").value(52))
                .andExpect(jsonPath("$.content[0].dryResidueWeightParallelFirst").value(10))
                .andExpect(jsonPath("$.content[0].dryResidueWeightParallelSecond").value(9))
                .andExpect(jsonPath("$.content[0].dryResidueWeightAverage").value(9.5));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        CarbohydratesResearchCreateDto dto = CarbohydratesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(2.)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .probeId(probe.getId())
                .build();

        MvcResult result = mvc.perform(post("/carbohydrates-researches")
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

        Optional<CarbohydratesResearch> createdEntity = repository.findById(id);
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
        CarbohydratesResearchCreateDto dto = CarbohydratesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(null)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .probeId(UUID.randomUUID())
                .build();

        mvc.perform(post("/carbohydrates-researches")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        CarbohydratesResearch entity = repository.save(new CarbohydratesResearchFactory().createSimple());
        CarbohydratesResearchUpdateDto dto = new CarbohydratesResearchUpdateDto(2., 1., 1., 1., 10.,10.);

        mvc.perform(put("/carbohydrates-researches/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<CarbohydratesResearch> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getByuksaParallelFirst(), dto.byuksaParallelFirst());
        assertEquals(updated.get().getByuksaParallelSecond(), dto.byuksaParallelSecond());
        assertEquals(updated.get().getByuksaAfterDryingParallelFirst(), dto.byuksaAfterDryingParallelFirst());
        assertEquals(updated.get().getByuksaAfterDryingParallelSecond(), dto.byuksaAfterDryingParallelSecond());
    }

    @Test
    void delete_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        CarbohydratesResearch entity = repository.save(new CarbohydratesResearchFactory().createSimple(probe));

        mvc.perform(delete("/carbohydrates-researches/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<CarbohydratesResearch> deleted = repository.findById(entity.getId());
        assertTrue(deleted.isEmpty());
        assertTrue(probeRepository.findById(probe.getId()).isPresent());
    }
}