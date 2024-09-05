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

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void contextLoads() {
    }

    @Test
    void getById_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        mvc.perform(get("/ingredient/{id}", entity.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.fats").value(entity.getFats()));
    }

    @Test
    void getAll_ok() throws Exception {
        var factory = new IngredientFactory();
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());

        mvc.perform(get("/ingredient").contentType(MediaType.APPLICATION_JSON))
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

        mvc.perform(get("/ingredient?search={name}", searched.getName().toLowerCase()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(searched.getId().toString()));
    }

    @Test
    void create_created() throws Exception {
        IngredientCreateDto dto = new IngredientCreateDto("name for test", 1f, 1f, 1f, 1f, 1f);

        MvcResult result = mvc.perform(post("/ingredient")
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
        IngredientCreateDto dto = new IngredientCreateDto("", 1f, 1f, 1f, 1f, 1f);

        mvc.perform(post("/ingredient")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());
        IngredientCreateDto dto = new IngredientCreateDto("name for test", 1f, 1f, 1f, 1f, 1f);

        mvc.perform(put("/ingredient/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Ingredient> updated = repository.findById(entity.getId());
        //noinspection OptionalGetWithoutIsPresent
        assertEquals(updated.get().getName(), dto.name());
    }

    @Test
    void delete_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        mvc.perform(delete("/ingredient/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Ingredient> deletedEntity = repository.findById(entity.getId());
        assertTrue(deletedEntity.isEmpty());
    }

}
