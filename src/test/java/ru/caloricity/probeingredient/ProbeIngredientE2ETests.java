package ru.caloricity.probeingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.caloricity.common.RestPageImpl;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.ingredient.IngredientFactory;
import ru.caloricity.ingredient.IngredientRepository;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.ProbeFactory;
import ru.caloricity.probe.ProbeRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProbeIngredientE2ETests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProbeIngredientRepository repository;
    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        probeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    void getById_ok() {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProbeIngredient entity = repository.save(new ProbeIngredientFactory().createSimple(probe, ingredient));

        ResponseEntity<ProbeIngredientDto> response = testRestTemplate.getForEntity("/probe-ingredient/{id}", ProbeIngredientDto.class, entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProbeIngredientDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(entity.getId(), responseBody.id());
        assertEquals(entity.getGross(), responseBody.gross());
        assertEquals(entity.getNet(), responseBody.net());
    }

    @Test
    void getById_notFound() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/probe-ingredient/{id}", String.class, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAll_ok() {
        var ingredientFactory = new IngredientFactory();
        Ingredient ingredient1 = ingredientRepository.save(ingredientFactory.createSimple());
        Ingredient ingredient2 = ingredientRepository.save(ingredientFactory.createSimple());
        Ingredient ingredient3 = ingredientRepository.save(ingredientFactory.createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        var factory = new ProbeIngredientFactory();
        repository.save(factory.createSimple(probe, ingredient1));
        repository.save(factory.createSimple(probe, ingredient2));
        repository.save(factory.createSimple(probe, ingredient3));

        ResponseEntity<RestPageImpl<ProbeIngredientInPageDto>> response = testRestTemplate.exchange(
                "/probe-ingredient",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestPageImpl<ProbeIngredientInPageDto> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(3, responseBody.getTotalElements());
        assertEquals(ingredient1.getName(), responseBody.getContent().get(0).ingredientName());
        assertEquals(48.5, responseBody.getContent().get(0).drySubstances());
        assertEquals(2.5, responseBody.getContent().get(0).proteins());
        assertEquals(0.5, responseBody.getContent().get(0).fats());
        assertEquals(1, responseBody.getContent().get(0).carbohydrates());    }

    @Test
    void create_created() {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        ProbeIngredientCreateDto dto = ProbeIngredientCreateDto.builder()
                .probeId(probe.getId())
                .ingredientId(ingredient.getId())
                .gross(2.)
                .net(3.)
                .build();

        ResponseEntity<IdDto> response = testRestTemplate.postForEntity("/probe-ingredient", dto, IdDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        IdDto responseBody = response.getBody();
        assertNotNull(responseBody);
        ProbeIngredient created = repository.findById(responseBody.id()).orElseThrow();
        assertEquals(probe.getId(), created.getProbe().getId());
        assertEquals(ingredient.getId(), created.getIngredient().getId());
        assertEquals(2., created.getGross());
        assertEquals(3., created.getNet());
    }

    @Test
    void create_badRequest_massSmallerThenZero() {
        ProbeIngredientCreateDto dto = ProbeIngredientCreateDto.builder()
                .probeId(UUID.randomUUID())
                .ingredientId(UUID.randomUUID())
                .gross(-40.)
                .net(3.)
                .build();

        ResponseEntity<String> response = testRestTemplate.postForEntity("/probe-ingredient", dto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_badRequest_referencedProbeNotExist() {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        ProbeIngredientCreateDto dto = ProbeIngredientCreateDto.builder()
                .probeId(UUID.randomUUID())
                .ingredientId(ingredient.getId())
                .gross(40.)
                .net(3.)
                .build();

        ResponseEntity<String> response = testRestTemplate.postForEntity("/probe-ingredient", dto, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void create_badRequest_referencedIngredientNotExist() {
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProbeIngredientCreateDto dto = ProbeIngredientCreateDto.builder()
                .probeId(probe.getId())
                .ingredientId(UUID.randomUUID())
                .gross(40.)
                .net(3.)
                .build();

        ResponseEntity<String> response = testRestTemplate.postForEntity("/probe-ingredient", dto, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void update_ok() {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());

        ProbeIngredient entity = repository.save(new ProbeIngredientFactory().createSimple(probe, ingredient));
        ProbeIngredientUpdateDto dto = new ProbeIngredientUpdateDto(1321., 2412.);

        testRestTemplate.put("/probe-ingredient/{id}", dto, entity.getId());

        ProbeIngredient updated = repository.findById(entity.getId()).orElseThrow();
        assertEquals(1321., updated.getGross());
        assertEquals(2412., updated.getNet());
    }

    @Test
    void delete_ok() {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        ProbeIngredient entity = repository.save(new ProbeIngredientFactory().createSimple(probe, ingredient));

        testRestTemplate.delete("/probe-ingredient/{id}", entity.getId());

        assertFalse(repository.findById(entity.getId()).isPresent());
    }
}