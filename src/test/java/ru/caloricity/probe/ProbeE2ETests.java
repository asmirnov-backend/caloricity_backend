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
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.ingredient.IngredientFactory;
import ru.caloricity.ingredient.IngredientRepository;
import ru.caloricity.probe.research.carbohydratesresearch.CarbohydratesResearch;
import ru.caloricity.probe.research.carbohydratesresearch.CarbohydratesResearchFactory;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchFactory;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearchFactory;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchFactory;
import ru.caloricity.probeingredient.ProbeIngredient;
import ru.caloricity.probeingredient.ProbeIngredientFactory;
import ru.caloricity.probeingredient.ProbeIngredientRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
    @Autowired
    private ProbeIngredientRepository probeIngredientRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getById_ok() throws Exception {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe entity = repository.save(new ProbeFactory().createSimple());

        FatsResearch fatsResearch = new FatsResearchFactory().createSimple(entity);
        DrySubstancesResearch drySubstancesResearch = new DrySubstancesResearchFactory().createSimple(entity);
        ProteinsResearch proteinsResearch = new ProteinsResearchFactory().createSimple(entity);
        CarbohydratesResearch carbohydratesResearch = new CarbohydratesResearchFactory().createSimple(entity);

        ProbeIngredient probeIngredient = probeIngredientRepository.save(new ProbeIngredientFactory().createSimple(entity, ingredient));
        Set<ProbeIngredient> probeIngredients = new HashSet<>();
        probeIngredients.add(probeIngredient);
        entity.setProbeIngredients(probeIngredients);

        entity.setFatsResearch(fatsResearch);
        entity.setProteinsResearch(proteinsResearch);
        entity.setDrySubstancesResearch(drySubstancesResearch);
        entity.setCarbohydratesResearch(carbohydratesResearch);

        mvc.perform(get("/probes/{id}", entity.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.bankaEmptyMass").value(entity.getBankaEmptyMass()))
                .andExpect(jsonPath("$.bankaWithProbeMass").value(entity.getBankaWithProbeMass()))
                .andExpect(jsonPath("$.code").value(entity.getCode()))
                .andExpect(jsonPath("$.type").value(entity.getType().toString()))
                .andExpect(jsonPath("$.massTheory").value(entity.getMassTheory()))
                .andExpect(jsonPath("$.massFact").value(10))
                .andExpect(jsonPath("$.minerals").value(0.12))
                .andExpect(jsonPath("$.fatsResearch.id").value(fatsResearch.getId().toString()))
                .andExpect(jsonPath("$.proteinsResearch.id").value(proteinsResearch.getId().toString()))
                .andExpect(jsonPath("$.drySubstancesResearch.id").value(drySubstancesResearch.getId().toString()))
                .andExpect(jsonPath("$.carbohydratesResearch.id").value(carbohydratesResearch.getId().toString()))
                .andExpect(jsonPath("$.theoreticalCaloricity").value(18.5));
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
        ProbeCreateDto dto = ProbeCreateDto.builder()
                .name("name for test")
                .type(ProbeType.FIRST)
                .code("f213")
                .massTheory(1.)
                .bankaEmptyMass(1.)
                .bankaWithProbeMass(2.)
                .build();

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
        assertEquals(createdEntity.get().getName(), dto.name());
        assertEquals(createdEntity.get().getType(), dto.type());
        assertEquals(createdEntity.get().getCode(), dto.code());
        assertEquals(createdEntity.get().getMassTheory(), dto.massTheory());
        assertEquals(createdEntity.get().getBankaEmptyMass(), dto.bankaEmptyMass());
        assertEquals(createdEntity.get().getBankaWithProbeMass(), dto.bankaWithProbeMass());
    }

    @Test
    void create_badRequest() throws Exception {
        ProbeCreateDto dto = ProbeCreateDto.builder()
                .name("name for test")
                .type(ProbeType.FIRST)
                .code("f213")
                .massTheory(-1.)
                .bankaEmptyMass(1.)
                .build();

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
        ProbeUpdateDto dto = ProbeUpdateDto.builder()
                .name("updated name")
                .code("updated code")
                .massTheory(2.)
                .bankaEmptyMass(2.)
                .bankaWithProbeMass(3.)
                .build();

        mvc.perform(put("/probes/{id}", entity.getId().toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Probe> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getName(), dto.name());
        assertEquals(updated.get().getCode(), dto.code());
        assertEquals(updated.get().getMassTheory(), dto.massTheory());
        assertEquals(updated.get().getBankaEmptyMass(), dto.bankaEmptyMass());
        assertEquals(updated.get().getBankaWithProbeMass(), dto.bankaWithProbeMass());
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