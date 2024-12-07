package ru.caloricity.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.caloricity.common.RestPageImpl;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.ProbeFactory;
import ru.caloricity.probe.ProbeRepository;
import ru.caloricity.probeingredient.ProbeIngredientFactory;
import ru.caloricity.probeingredient.ProbeIngredientRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IngredientE2ETests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private IngredientRepository repository;
    @Autowired
    private ProbeIngredientRepository probeIngredientRepository;
    @Autowired
    private ProbeRepository probeRepository;

    @BeforeEach
    void setUp() {
        probeIngredientRepository.deleteAll();
        probeRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    void getById_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        ResponseEntity<IngredientDto> response = testRestTemplate.getForEntity("/ingredients/{id}", IngredientDto.class, entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        IngredientDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(entity.getId(), responseBody.id());
        assertEquals(entity.getName(), responseBody.name());
        assertEquals(entity.getFats(), responseBody.fats());
        assertEquals(entity.getCarbohydrates(), responseBody.carbohydrates());
        assertEquals(entity.getProteins(), responseBody.proteins());
        assertEquals(entity.getEdiblePart(), responseBody.ediblePart());
        assertEquals(entity.getWater(), responseBody.water());
    }

    @Test
    void getById_notFound() throws Exception {
        UUID id = UUID.randomUUID();
        ResponseEntity<String> response = testRestTemplate.getForEntity("/ingredients/{id}", String.class, id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertThat(response.getBody()).contains(id.toString());
    }

    @Test
    void getAll_ok() throws Exception {
        var factory = new IngredientFactory();
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());

        ResponseEntity<RestPageImpl<IngredientInPageDto>> response = testRestTemplate.exchange(
                "/ingredients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getTotalElements());
    }

    @Test
    void getAllWithSearch_ok() throws Exception {
        var factory = new IngredientFactory();
        Ingredient searched = factory.createSimple();
        searched.setName("Searched name");
        repository.save(searched);
        repository.save(factory.createSimple());
        repository.save(factory.createSimple());

        ResponseEntity<RestPageImpl<IngredientInPageDto>> response = testRestTemplate.exchange(
                "/ingredients?search={name}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                searched.getName().toLowerCase()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestPageImpl<IngredientInPageDto> ingredientList = response.getBody();
        assertNotNull(ingredientList);
        assertEquals(1, ingredientList.getTotalElements());
        assertEquals(searched.getId(), ingredientList.getContent().getFirst().id());
    }

    @Test
    void create_created() throws Exception {
        IngredientCreateDto dto = IngredientCreateDto.builder()
                .name("name for test")
                .ediblePart(1.)
                .water(1.)
                .proteins(1.)
                .fats(1.)
                .carbohydrates(1.)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<IngredientCreateDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<IngredientDto> response = testRestTemplate.postForEntity("/ingredients", request, IngredientDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        IngredientDto responseBody = response.getBody();
        assertNotNull(responseBody);
        UUID id = responseBody.id();
        Optional<Ingredient> createdEntity = repository.findById(id);
        assertTrue(createdEntity.isPresent());
        assertEquals(createdEntity.get().getName(), dto.name());
        assertEquals(createdEntity.get().getEdiblePart(), dto.ediblePart());
        assertEquals(createdEntity.get().getWater(), dto.water());
        assertEquals(createdEntity.get().getProteins(), dto.proteins());
        assertEquals(createdEntity.get().getFats(), dto.fats());
        assertEquals(createdEntity.get().getCarbohydrates(), dto.carbohydrates());
    }

    @Test
    void create_badRequest() throws Exception {
        IngredientCreateDto dto = IngredientCreateDto.builder()
                .name("")
                .ediblePart(1.)
                .water(1.)
                .proteins(1.)
                .fats(1.)
                .carbohydrates(1.)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<IngredientCreateDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity("/ingredients", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
//        assertThat(response.getBody()).contains("name: must not be blank");
//        assertThat(response.getBody()).contains("name: size must be between 2");
    }

    @Test
    void update_ok() throws Exception {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());
        IngredientUpdateDto dto = IngredientUpdateDto.builder()
                .name("updated name")
                .ediblePart(0.5)
                .water(2.)
                .proteins(2.)
                .fats(2.)
                .carbohydrates(2.)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<IngredientUpdateDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Void> response = testRestTemplate.exchange("/ingredients/{id}", HttpMethod.PUT, request, Void.class, entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Ingredient> updated = repository.findById(entity.getId());
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getName(), dto.name());
        assertEquals(updated.get().getEdiblePart(), dto.ediblePart());
        assertEquals(updated.get().getWater(), dto.water());
        assertEquals(updated.get().getProteins(), dto.proteins());
        assertEquals(updated.get().getFats(), dto.fats());
        assertEquals(updated.get().getCarbohydrates(), dto.carbohydrates());
    }

    @Test
    void delete_ok() {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());

        ResponseEntity<Void> response = testRestTemplate.exchange("/ingredients/{id}", HttpMethod.DELETE, null, Void.class, entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Ingredient> deletedEntity = repository.findById(entity.getId());
        assertTrue(deletedEntity.isEmpty());
    }

    @Test
    void delete_throwCascadeDeleteRestrictException() {
        Ingredient entity = repository.save(new IngredientFactory().createSimple());
        Probe probe = probeRepository.save(new ProbeFactory().createSimple());
        probeIngredientRepository.save(new ProbeIngredientFactory().createSimple(probe, entity));

        ResponseEntity<String> response = testRestTemplate.exchange("/ingredients/{id}", HttpMethod.DELETE, null, String.class, entity.getId());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}