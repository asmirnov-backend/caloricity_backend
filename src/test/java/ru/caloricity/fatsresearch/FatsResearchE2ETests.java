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
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        repository.save(new FatsResearchFactory().createSimple(probe));

        mvc.perform(get("/fats-researches?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.content[0].patronMassBeforeExtractionParallelFirst").value(8))
                .andExpect(jsonPath("$.content[0].patronMassBeforeExtractionParallelSecond").value(9));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        FatsResearchCreateDto dto = new FatsResearchCreateDto(1f, 2f, 1f, 2f, probe.getId());

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
        FatsResearchCreateDto dto = new FatsResearchCreateDto(1f, null, 1f, 2f, UUID.randomUUID());

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
        FatsResearchUpdateDto dto = new FatsResearchUpdateDto(2f, 2f, 1f, 2f);

        mvc.perform(put("/fats-researches/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<FatsResearch> updated = repository.findById(entity.getId());
        //noinspection OptionalGetWithoutIsPresent
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
