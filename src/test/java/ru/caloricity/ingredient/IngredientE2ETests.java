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
    void getAll_ok() throws Exception {
        repository.save(new IngredientFactory().createSimple());
        repository.save(new IngredientFactory().createSimple());
        repository.save(new IngredientFactory().createSimple());

        mvc.perform(get("/caloricity/ingredient").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(2)));
    }

    @Test
    void create_created() throws Exception {
        IngredientCreateDto dto = new IngredientCreateDto(1,2);

        MvcResult result = mvc.perform(post("/caloricity/ingredient")
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
        IngredientCreateDto dto = new IngredientCreateDto(2,2);
        dto.setNet(-23);

        mvc.perform(post("/caloricity/ingredient")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());
        IngredientCreateDto dto = new IngredientCreateDto(100,222);

        mvc.perform(put("/caloricity/ingredient/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Ingredient> updated = repository.findById(entity.getId());
        assertEquals(updated.get().getNet(), dto.getNet());
    }

    @Test
    void delete_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        mvc.perform(delete("/caloricity/ingredient/{id}", entity.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Ingredient> deleted = repository.findById(entity.getId());
        assertTrue(deleted.isEmpty());
    }

}
