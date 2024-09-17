package ru.caloricity.probeingredient;

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
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.ingredient.IngredientFactory;
import ru.caloricity.ingredient.IngredientRepository;
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
class ProbeIngredientE2ETests {


    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProbeIngredientRepository repository;
    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_ok() throws Exception {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProbeIngredient entity = repository.save(new ProbeIngredientFactory().createSimple(probe, ingredient));

        mvc.perform(get("/probe-ingredient/{id}", entity.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.gross").value(entity.getGross()))
                .andExpect(jsonPath("$.net").value(entity.getNet()));
    }

    @Test
    void getById_notFound() throws Exception {
        mvc.perform(get("/probe-ingredient/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void getAll_ok() throws Exception {
        var ingredientFactory = new IngredientFactory();
        Ingredient ingredient1 = ingredientRepository.save(ingredientFactory.createSimple());
        Ingredient ingredient2 = ingredientRepository.save(ingredientFactory.createSimple());
        Ingredient ingredient3 = ingredientRepository.save(ingredientFactory.createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        var factory = new ProbeIngredientFactory();
        repository.save(factory.createSimple(probe, ingredient1));
        repository.save(factory.createSimple(probe, ingredient2));
        repository.save(factory.createSimple(probe, ingredient3));

        mvc.perform(get("/probe-ingredient").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(2)))
                .andExpect(jsonPath("$.content[0].ingredientName").value(ingredient1.getName()))
                .andExpect(jsonPath("$.content[0].water").value(37))
                .andExpect(jsonPath("$.content[0].proteins").value(62))
                .andExpect(jsonPath("$.content[0].fats").value(12))
                .andExpect(jsonPath("$.content[0].carbohydrates").value(25));
    }


    @Test
    void create_created() throws Exception {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        ProbeIngredientCreateDto dto = new ProbeIngredientCreateDto(probe.getId(), ingredient.getId(), 2f, 3f);

        MvcResult result = mvc.perform(post("/probe-ingredient")
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

        Optional<ProbeIngredient> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
    }

    @Test
    void create_badRequest_massSmallerThenZero() throws Exception {
        ProbeIngredientCreateDto dto = new ProbeIngredientCreateDto(UUID.randomUUID(), UUID.randomUUID(), -40f, 3f);

        mvc.perform(post("/probe-ingredient")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_badRequest_referencedProbeNotExist() throws Exception {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        ProbeIngredientCreateDto dto = new ProbeIngredientCreateDto(UUID.randomUUID(), ingredient.getId(), 40f, 3f);

        mvc.perform(post("/probe-ingredient")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void create_badRequest_referencedIngredientNotExist() throws Exception {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProbeIngredientCreateDto dto = new ProbeIngredientCreateDto(probe.getId(), UUID.randomUUID(), 40f, 3f);

        mvc.perform(post("/probe-ingredient")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void update_ok() throws Exception {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        ProbeIngredient entity = repository.save(new ProbeIngredientFactory().createSimple(probe, ingredient));
        ProbeIngredientUpdateDto dto = new ProbeIngredientUpdateDto(1321f, 2412f);

        mvc.perform(put("/probe-ingredient/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ProbeIngredient> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getGross(), dto.gross());
        assertEquals(updated.get().getNet(), dto.net());
    }

    @Test
    void delete_ok() throws Exception {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        ProbeIngredient entity = repository.save(new ProbeIngredientFactory().createSimple(probe, ingredient));

        mvc.perform(delete("/probe-ingredient/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ProbeIngredient> deletedEntity = repository.findById(entity.getId());
        assertTrue(deletedEntity.isEmpty());
    }

}
