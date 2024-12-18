package ru.caloricity.proteinsresearch;

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
class ProteinsResearchE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProteinsResearchRepository repository;
    @Autowired
    private ProbeRepository probeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/proteins-researches/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        repository.save(new ProteinsResearchFactory().createSimple(probe));

        mvc.perform(get("/proteins-researches?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.content[0].titrantVolumeParallelFirst").value(10))
                .andExpect(jsonPath("$.content[0].titrantVolumeParallelSecond").value(20))
                .andExpect(jsonPath("$.content[0].massNaveskiParallelFirst").value(10))
                .andExpect(jsonPath("$.content[0].massNaveskiParallelSecond").value(10))
                .andExpect(jsonPath("$.content[0].coefficient").value(0.95))
                .andExpect(jsonPath("$.content[0].controlVolume").value(5));
    }

    @Test
    void create_created() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProteinsResearchCreateDto dto = ProteinsResearchCreateDto.builder()
                .titrantVolumeParallelFirst(1.)
                .titrantVolumeParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .controlVolume(10.)
                .coefficient(10.)
                .probeId(probe.getId())
                .build();

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
        assertEquals(createdEntity.get().getTitrantVolumeParallelFirst(), dto.titrantVolumeParallelFirst());
        assertEquals(createdEntity.get().getTitrantVolumeParallelSecond(), dto.titrantVolumeParallelSecond());
        assertEquals(createdEntity.get().getControlVolume(), dto.controlVolume());
        assertEquals(createdEntity.get().getCoefficient(), dto.coefficient());
    }

    @Test
    void create_badRequest() throws Exception {
        ProteinsResearchCreateDto dto = ProteinsResearchCreateDto.builder()
                .titrantVolumeParallelFirst(1.)
                .titrantVolumeParallelSecond(null)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .controlVolume(10.)
                .coefficient(10.)
                .probeId(UUID.randomUUID())
                .build();

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
        ProteinsResearchUpdateDto dto = new ProteinsResearchUpdateDto(2., 1., 1., 1.,10.,10.);

        mvc.perform(put("/proteins-researches/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ProteinsResearch> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getTitrantVolumeParallelFirst(), dto.titrantVolumeParallelFirst());
        assertEquals(updated.get().getTitrantVolumeParallelSecond(), dto.titrantVolumeParallelSecond());
        assertEquals(updated.get().getControlVolume(), dto.controlVolume());
        assertEquals(updated.get().getCoefficient(), dto.coefficient());
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