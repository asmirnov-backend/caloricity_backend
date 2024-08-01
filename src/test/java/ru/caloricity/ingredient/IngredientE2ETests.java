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
import ru.caloricity.ingredientCatalog.IngredientCatalog;
import ru.caloricity.ingredientCatalog.IngredientCatalogFactory;
import ru.caloricity.ingredientCatalog.IngredientCatalogRepository;
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
class IngredientE2ETests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IngredientRepository repository;
    @Autowired
    private IngredientCatalogRepository ingredientCatalogRepository;
    @Autowired
    private ProbeRepository probeRepository;

    @Test
    void contextLoads() {
    }
    
    @Test
    void getAll_ok() throws Exception {
        IngredientCatalog ingredientCatalog = ingredientCatalogRepository.save(new IngredientCatalogFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        repository.save(new IngredientFactory().createSimple(ingredientCatalog, probe));
        repository.save(new IngredientFactory().createSimple(ingredientCatalog, probe));
        repository.save(new IngredientFactory().createSimple(ingredientCatalog, probe));

        mvc.perform(get("/caloricity/ingredient?probe-id={probeId}", probe.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(greaterThan(2)))
                .andExpect(jsonPath("$.content[0].name").value(ingredientCatalog.getName()))
                .andExpect(jsonPath("$.content[0].water").value(1))
                .andExpect(jsonPath("$.content[0].proteins").value(1))
                .andExpect(jsonPath("$.content[0].fats").value(1))
                .andExpect(jsonPath("$.content[0].carbohydrates").value(1));
    }

    @Test
    void create_created() throws Exception {
        IngredientCatalog ingredientCatalog = ingredientCatalogRepository.save(new IngredientCatalogFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        IngredientCreateDto dto = new IngredientCreateDto(1,2, ingredientCatalog.getId(), probe.getId());

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
        assertNotNull(createdEntity.get().getIngredientInCatalog());
        assertEquals(createdEntity.get().getIngredientInCatalog().getId(), ingredientCatalog.getId());
        assertNotNull(createdEntity.get().getProbe());
        assertEquals(createdEntity.get().getProbe().getId(), probe.getId());

    }

    @Test
    void create_badRequest() throws Exception {
        IngredientCreateDto dto = new IngredientCreateDto(2,-2, UUID.randomUUID(), UUID.randomUUID());

        mvc.perform(post("/caloricity/ingredient")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
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
