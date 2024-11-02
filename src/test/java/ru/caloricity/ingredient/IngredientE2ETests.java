package ru.caloricity.ingredient;

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
import ru.caloricity.common.exception.CascadeDeleteRestrictException;
import ru.caloricity.common.exception.EntityNotFoundException;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.ProbeFactory;
import ru.caloricity.probe.ProbeRepository;
import ru.caloricity.probeingredient.ProbeIngredient;
import ru.caloricity.probeingredient.ProbeIngredientFactory;
import ru.caloricity.probeingredient.ProbeIngredientRepository;

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
class IngredientE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IngredientRepository repository;
    @Autowired
    private ProbeIngredientRepository probeIngredientRepository;
    @Autowired
    private ProbeRepository probeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        mvc.perform(get("/ingredients/{id}", entity.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.fats").value(entity.getFats()))
                .andExpect(jsonPath("$.carbohydrates").value(entity.getCarbohydrates()))
                .andExpect(jsonPath("$.proteins").value(entity.getProteins()))
                .andExpect(jsonPath("$.ediblePart").value(entity.getEdiblePart()))
                .andExpect(jsonPath("$.water").value(entity.getWater()));
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/ingredients/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        var factory = new IngredientFactory();
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());

        mvc.perform(get("/ingredients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(2)));
    }

    @Test
    void getAllWithSearch_ok() throws Exception {
        var factory = new IngredientFactory();
        Ingredient searched = factory.createSimple();
        searched.setName("Searched name");
        repository.save(searched);
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());

        mvc.perform(get("/ingredients?search={name}", searched.getName().toLowerCase()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(searched.getId().toString()));
    }

    @Test
    void create_created() throws Exception {
        IngredientCreateDto dto = new IngredientCreateDto("name for test", 1., 1., 1., 1., 1.);

        MvcResult result = mvc.perform(post("/ingredients")
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

        Optional<Ingredient> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
    }

    @Test
    void create_badRequest() throws Exception {
        IngredientCreateDto dto = new IngredientCreateDto("", 1., 1., 1., 1., 1.);

        mvc.perform(post("/ingredients")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());
        IngredientCreateDto dto = new IngredientCreateDto("name for test", 1., 1., 1., 1., 1.);

        mvc.perform(put("/ingredients/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Ingredient> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getName(), dto.name());
    }

    @Test
    void delete_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        mvc.perform(delete("/ingredients/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Ingredient> deletedEntity = repository.findById(entity.getId());
        assertTrue(deletedEntity.isEmpty());
    }

    @Test
    void delete_throwCascadeDeleteRestrictException() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProbeIngredient probeIngredient = probeIngredientRepository.save(new ProbeIngredientFactory().createSimple(probe, entity));

        mvc.perform(delete("/ingredients/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(CascadeDeleteRestrictException.class, result.getResolvedException()));
    }

}
