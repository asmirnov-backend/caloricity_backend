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
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.ProbeFactory;
import ru.caloricity.probe.ProbeRepository;

import java.util.Optional;
import java.util.UUID;

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
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        repository.save(new CarbohydratesResearchFactory().createSimple(probe));

        mvc.perform(get("/carbohydrates-research?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].byuksaParallelFirst").value(11))
                .andExpect(jsonPath("$.content[0].byuksaParallelSecond").value(12));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        CarbohydratesResearchCreateDto dto = new CarbohydratesResearchCreateDto(1f, 2f, 10f, 10f, probe.getId());

        MvcResult result = mvc.perform(post("/carbohydrates-research")
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
    }

    @Test
    void create_badRequest() throws Exception {
        CarbohydratesResearchCreateDto dto = new CarbohydratesResearchCreateDto(1f, null, 10f, 10f, UUID.randomUUID());

        mvc.perform(post("/carbohydrates-research")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        CarbohydratesResearch entity = repository.save(new CarbohydratesResearchFactory().createSimple());
        CarbohydratesResearchUpdateDto dto = new CarbohydratesResearchUpdateDto(2f, 1f, 1f, 1f);

        mvc.perform(put("/carbohydrates-research/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<CarbohydratesResearch> updated = repository.findById(entity.getId());
        //noinspection OptionalGetWithoutIsPresent
        assertEquals(updated.get().getByuksaParallelSecond(), dto.byuksaAfterDryingParallelSecond());
    }

    @Test
    void delete_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        CarbohydratesResearch entity = repository.save(new CarbohydratesResearchFactory().createSimple(probe));

        mvc.perform(delete("/carbohydrates-research/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<CarbohydratesResearch> deleted = repository.findById(entity.getId());
        assertTrue(deleted.isEmpty());
        assertTrue(probeRepository.findById(probe.getId()).isPresent());
    }

}
