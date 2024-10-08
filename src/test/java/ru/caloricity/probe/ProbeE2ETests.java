package ru.caloricity.probe;

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
class ProbeE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProbeRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_ok() throws Exception {
        Probe entity = repository.save(new ProbeFactory().createSimple());

        mvc.perform(get("/probes/{id}", entity.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.bankaEmptyMass").value(entity.getBankaEmptyMass()))
                .andExpect(jsonPath("$.bankaWithProbeMass").value(entity.getBankaWithProbeMass()));
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/probes/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        var probeFactory = new ProbeFactory();
        repository.save(probeFactory.createSimple());
        repository.save(probeFactory.createSimple());
        repository.save(probeFactory.createSimple());

        mvc.perform(get("/probes").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(2)));
    }

    @Test
    void getAllWithSearch_ok() throws Exception {
        var probeFactory = new ProbeFactory();
        Probe searchedEntity = probeFactory.createSimple();
        searchedEntity.setCode("F123-563");
        repository.save(searchedEntity);
        repository.save(probeFactory.createSimple());
        repository.save(probeFactory.createSimple());

        mvc.perform(get("/probes?search={code}", searchedEntity.getCode().toLowerCase()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(searchedEntity.getId().toString()));
    }

    @Test
    void create_created() throws Exception {
        ProbeCreateDto dto = new ProbeCreateDto("name for test", ProbeType.FIRST, "f213", 1f, 1f, 2f);

        MvcResult result = mvc.perform(post("/probes")
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

        Optional<Probe> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
    }

    @Test
    void create_badRequest() throws Exception {
        ProbeCreateDto dto = new ProbeCreateDto("", ProbeType.FIRST, "f213", 1f, 1f, 2f);

        mvc.perform(post("/probes")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        Probe entity = repository.save(new ProbeFactory().createSimple());
        ProbeUpdateDto dto = new ProbeUpdateDto("name for test132", "f213", 1f, 1f, 2f);

        mvc.perform(put("/probes/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Probe> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getName(), dto.name());
    }

    @Test
    void delete_ok() throws Exception {
        Probe entity = repository.save(new ProbeFactory().createSimple());

        mvc.perform(delete("/probes/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Probe> deletedEntity = repository.findById(entity.getId());
        assertTrue(deletedEntity.isEmpty());
    }

}
