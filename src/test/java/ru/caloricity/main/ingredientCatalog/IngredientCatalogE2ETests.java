package ru.caloricity.main.ingredientCatalog;

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
import ru.caloricity.main.caloricity.ingredientCatalog.IngredientCatalog;
import ru.caloricity.main.caloricity.ingredientCatalog.IngredientCatalogCreateDto;
import ru.caloricity.main.caloricity.ingredientCatalog.IngredientCatalogFactory;
import ru.caloricity.main.caloricity.ingredientCatalog.IngredientCatalogRepository;

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
class IngredientCatalogE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IngredientCatalogRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_ok() throws Exception {
        IngredientCatalog catalog = repository.save(new IngredientCatalogFactory().createSimple());

        mvc.perform(get("/caloricity/ingredient-catalog/{id}", catalog.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(catalog.getId().toString()))
                .andExpect(jsonPath("$.name").value(catalog.getName()))
                .andExpect(jsonPath("$.fats").value(catalog.getFats()));
    }

    @Test
    void getAll_ok() throws Exception {
        repository.save(new IngredientCatalogFactory().createSimple());
        repository.save(new IngredientCatalogFactory().createSimple());
        repository.save(new IngredientCatalogFactory().createSimple());

        mvc.perform(get("/caloricity/ingredient-catalog").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(2)));
    }

    @Test
    void getAllWithSearch_ok() throws Exception {
        IngredientCatalog searchedCatalog = new IngredientCatalogFactory().createSimple();
        searchedCatalog.setName("Searched name");
        repository.save(searchedCatalog);
        repository.save(new IngredientCatalogFactory().createSimple());
        repository.save(new IngredientCatalogFactory().createSimple());

        mvc.perform(get("/caloricity/ingredient-catalog?search={name}", searchedCatalog.getName().toLowerCase()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(searchedCatalog.getId().toString()));
    }

    @Test
    void create_created() throws Exception {
        IngredientCatalogCreateDto dto = new IngredientCatalogCreateDto("name for test", 1,1,1,1,1);

        MvcResult result = mvc.perform(post("/caloricity/ingredient-catalog")
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

        Optional<IngredientCatalog> catalog1 = repository.findById(id);
        assertTrue(catalog1.isPresent());
    }

    @Test
    void create_badRequest() throws Exception {
        IngredientCatalogCreateDto dto = new IngredientCatalogCreateDto("name for test", 1,1,1,1,1);
        dto.setName("");

        mvc.perform(post("/caloricity/ingredient-catalog")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_ok() throws Exception {
        IngredientCatalog catalog = repository.save(new IngredientCatalogFactory().createSimple());
        IngredientCatalogCreateDto dto = new IngredientCatalogCreateDto("name for test", 1,1,1,1,1);

        mvc.perform(put("/caloricity/ingredient-catalog/{id}", catalog.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<IngredientCatalog> updated = repository.findById(catalog.getId());
        assertEquals(updated.get().getName(), dto.getName());
    }

    @Test
    void delete_ok() throws Exception {
        IngredientCatalog catalog = repository.save(new IngredientCatalogFactory().createSimple());

        mvc.perform(delete("/caloricity/ingredient-catalog/{id}", catalog.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<IngredientCatalog> catalog1 = repository.findById(catalog.getId());
        assertTrue(catalog1.isEmpty());
    }

}
