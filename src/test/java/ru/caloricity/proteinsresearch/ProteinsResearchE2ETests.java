package ru.caloricity.proteinsresearch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProteinsResearchE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProteinsResearchRepository repository;
    @Autowired
    private ProbeRepository probeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int localServerPort;

    @Test
    void contextLoads() {
    }

    @Test
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        repository.save(new ProteinsResearchFactory().createSimple(probe));

        mvc.perform(get("/proteins-researches?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.content[0].titrantVolumeParallelSecond").value(20))
                .andExpect(jsonPath("$.content[0].coefficient").value(0.95f))
                .andExpect(jsonPath("$.content[0].controlVolume").value(5));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProteinsResearchCreateDto dto = new ProteinsResearchCreateDto(1f, 2f, 10f, 10f, probe.getId());

        MvcResult result = mvc.perform(post("/proteins-researches")
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

        Optional<ProteinsResearch> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
        assertNotNull(createdEntity.get().getProbe());
        assertEquals(createdEntity.get().getProbe().getId(), probe.getId());
    }

    @Test
    void create_badRequest() throws Exception {
        ProteinsResearchCreateDto dto = new ProteinsResearchCreateDto(1f, null, 10f, 10f, UUID.randomUUID());

        mvc.perform(post("/proteins-researches")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        ProteinsResearch entity = repository.save(new ProteinsResearchFactory().createSimple());
        ProteinsResearchUpdateDto dto = new ProteinsResearchUpdateDto(2f, 1f, 1f, 1f);

        mvc.perform(put("/proteins-researches/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ProteinsResearch> updated = repository.findById(entity.getId());
        //noinspection OptionalGetWithoutIsPresent
        assertEquals(updated.get().getTitrantVolumeParallelSecond(), dto.titrantVolumeParallelSecond());
    }

    @Test
    void delete_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProteinsResearch entity = repository.save(new ProteinsResearchFactory().createSimple(probe));

        mvc.perform(delete("/proteins-researches/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ProteinsResearch> deleted = repository.findById(entity.getId());
        assertTrue(deleted.isEmpty());
        assertTrue(probeRepository.findById(probe.getId()).isPresent());
    }
}
