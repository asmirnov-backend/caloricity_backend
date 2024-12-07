package ru.caloricity.probe;

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
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchCreateDto;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchFactory;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchRepository;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearchCreateDto;
import ru.caloricity.probe.research.fatsresearch.FatsResearchFactory;
import ru.caloricity.probe.research.fatsresearch.FatsResearchRepository;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchCreateDto;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchFactory;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchRepository;
import ru.caloricity.probeingredient.ProbeIngredient;
import ru.caloricity.probeingredient.ProbeIngredientFactory;
import ru.caloricity.probeingredient.ProbeIngredientRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProbeE2ETests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProbeRepository repository;
    @Autowired
    private ProbeIngredientRepository probeIngredientRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private FatsResearchRepository fatsResearchRepository;
    @Autowired
    private DrySubstancesResearchRepository drySubstancesResearchRepository;
    @Autowired
    private ProteinsResearchRepository proteinsResearchRepository;

    @BeforeEach
    void setUp() {
        probeIngredientRepository.deleteAll();
        fatsResearchRepository.deleteAll();
        drySubstancesResearchRepository.deleteAll();
        probeIngredientRepository.deleteAll();
        repository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    void getById_ok() {
        Ingredient ingredient = ingredientRepository.save(new IngredientFactory().createSimple());
        Probe entity = new ProbeFactory().createSimple();
        FatsResearch fatsResearch = new FatsResearchFactory().createSimple(entity);
        DrySubstancesResearch drySubstancesResearch = new DrySubstancesResearchFactory().createSimple(entity);
        ProteinsResearch proteinsResearch = new ProteinsResearchFactory().createSimple(entity);
        ProbeIngredient probeIngredient = new ProbeIngredientFactory().createSimple(entity, ingredient);
        entity.setFatsResearch(fatsResearch);
        entity.setProteinsResearch(proteinsResearch);
        entity.setDrySubstancesResearch(drySubstancesResearch);
        repository.save(entity);
        probeIngredientRepository.save(probeIngredient);

        ResponseEntity<ProbeDto> response = testRestTemplate.getForEntity("/probes/{id}", ProbeDto.class, entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProbeDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(entity.getId(), responseBody.id());
        assertEquals(entity.getName(), responseBody.name());
        assertEquals(entity.getBankaEmptyMass(), responseBody.bankaEmptyMass());
        assertEquals(entity.getBankaWithProbeMass(), responseBody.bankaWithProbeMass());
        assertEquals(entity.getCode(), responseBody.code());
        assertEquals(entity.getType(), responseBody.type());
        assertEquals(entity.getMassTheory(), responseBody.massTheory());
        assertEquals(10, responseBody.massFact());
        assertEquals(0.12, responseBody.minerals());
        assertEquals(fatsResearch.getId(), responseBody.fatsResearch().id());
        assertEquals(proteinsResearch.getId(), responseBody.proteinsResearch().id());
        assertEquals(drySubstancesResearch.getId(), responseBody.drySubstancesResearch().id());
        assertEquals(18.5, responseBody.theoreticalCaloricity());
    }

    @Test
    void getById_notFound() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/probes/{id}", String.class, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAll_ok() {
        var probeFactory = new ProbeFactory();
        repository.save(probeFactory.createSimple());
        repository.save(probeFactory.createSimple());
        repository.save(probeFactory.createSimple());

        ResponseEntity<RestPageImpl<ProbeDto>> response = testRestTemplate.exchange(
                "/probes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestPageImpl<ProbeDto> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.getTotalElements() > 2);
    }

    @Test
    void getAllWithSearch_ok() {
        var probeFactory = new ProbeFactory();
        Probe searchedEntity = probeFactory.createSimple();
        searchedEntity.setCode("F123-563");
        repository.save(searchedEntity);
        repository.save(probeFactory.createSimple());
        repository.save(probeFactory.createSimple());

        ResponseEntity<RestPageImpl<ProbeDto>> response = testRestTemplate.exchange(
                "/probes?search={code}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                searchedEntity.getCode().toLowerCase()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestPageImpl<ProbeDto> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.getTotalElements());
        assertEquals(searchedEntity.getId(), responseBody.getContent().get(0).id());
    }

    @Test
    void create_created_withAllResearches() {
        FatsResearchCreateDto fatsResearchCreateDto = FatsResearchCreateDto.builder()
                .patronMassBeforeExtractionParallelFirst(1.)
                .patronMassBeforeExtractionParallelSecond(2.)
                .patronMassAfterExtractionParallelFirst(1.)
                .patronMassAfterExtractionParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .build();

        DrySubstancesResearchCreateDto drySubstancesResearchCreateDto = DrySubstancesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(2.)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .build();

        ProteinsResearchCreateDto proteinsResearchCreateDto = ProteinsResearchCreateDto.builder()
                .titrantVolumeParallelFirst(1.)
                .titrantVolumeParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .controlVolume(10.)
                .coefficient(10.)
                .build();

        ProbeCreateDto dto = ProbeCreateDto.builder()
                .name("name for test")
                .type(ProbeType.FIRST)
                .code("f213")
                .massTheory(1.)
                .bankaEmptyMass(1.)
                .bankaWithProbeMass(2.)
                .drySubstancesResearch(drySubstancesResearchCreateDto)
                .fatsResearch(fatsResearchCreateDto)
                .proteinsResearchCreateDto(proteinsResearchCreateDto)
                .build();

        ResponseEntity<IdDto> response = testRestTemplate.postForEntity("/probes", dto, IdDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        IdDto responseBody = response.getBody();
        assertNotNull(responseBody);
        Probe createdEntity = repository.findById(responseBody.id()).orElseThrow();
        assertEquals(dto.name(), createdEntity.getName());
        assertEquals(dto.type(), createdEntity.getType());
        assertEquals(dto.code(), createdEntity.getCode());
        assertEquals(dto.massTheory(), createdEntity.getMassTheory());
        assertEquals(dto.bankaEmptyMass(), createdEntity.getBankaEmptyMass());
        assertEquals(dto.bankaWithProbeMass(), createdEntity.getBankaWithProbeMass());

        assertNotNull(createdEntity.getFatsResearch());
        assertNotNull(createdEntity.getDrySubstancesResearch());
        assertNotNull(createdEntity.getProteinsResearch());

        assertEquals(createdEntity.getFatsResearch().getPatronMassBeforeExtractionParallelFirst(), fatsResearchCreateDto.patronMassBeforeExtractionParallelFirst());
        assertEquals(createdEntity.getFatsResearch().getPatronMassBeforeExtractionParallelSecond(), fatsResearchCreateDto.patronMassBeforeExtractionParallelSecond());
        assertEquals(createdEntity.getFatsResearch().getPatronMassAfterExtractionParallelFirst(), fatsResearchCreateDto.patronMassAfterExtractionParallelFirst());
        assertEquals(createdEntity.getFatsResearch().getPatronMassAfterExtractionParallelSecond(), fatsResearchCreateDto.patronMassAfterExtractionParallelSecond());
        assertEquals(createdEntity.getFatsResearch().getMassNaveskiParallelFirst(), fatsResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(createdEntity.getFatsResearch().getMassNaveskiParallelSecond(), fatsResearchCreateDto.massNaveskiParallelSecond());

        assertEquals(createdEntity.getDrySubstancesResearch().getByuksaParallelFirst(), drySubstancesResearchCreateDto.byuksaParallelFirst());
        assertEquals(createdEntity.getDrySubstancesResearch().getByuksaParallelSecond(), drySubstancesResearchCreateDto.byuksaParallelSecond());
        assertEquals(createdEntity.getDrySubstancesResearch().getByuksaAfterDryingParallelFirst(), drySubstancesResearchCreateDto.byuksaAfterDryingParallelFirst());
        assertEquals(createdEntity.getDrySubstancesResearch().getByuksaAfterDryingParallelSecond(), drySubstancesResearchCreateDto.byuksaAfterDryingParallelSecond());
        assertEquals(createdEntity.getDrySubstancesResearch().getMassNaveskiParallelFirst(), drySubstancesResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(createdEntity.getDrySubstancesResearch().getMassNaveskiParallelSecond(), drySubstancesResearchCreateDto.massNaveskiParallelSecond());

        assertEquals(createdEntity.getProteinsResearch().getTitrantVolumeParallelFirst(), proteinsResearchCreateDto.titrantVolumeParallelFirst());
        assertEquals(createdEntity.getProteinsResearch().getTitrantVolumeParallelSecond(), proteinsResearchCreateDto.titrantVolumeParallelSecond());
        assertEquals(createdEntity.getProteinsResearch().getMassNaveskiParallelFirst(), proteinsResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(createdEntity.getProteinsResearch().getMassNaveskiParallelSecond(), proteinsResearchCreateDto.massNaveskiParallelSecond());
        assertEquals(createdEntity.getProteinsResearch().getControlVolume(), proteinsResearchCreateDto.controlVolume());
        assertEquals(createdEntity.getProteinsResearch().getCoefficient(), proteinsResearchCreateDto.coefficient());
    }

    @Test
    void create_badRequest() {
        ProbeCreateDto dto = ProbeCreateDto.builder()
                .name("name for test")
                .type(ProbeType.FIRST)
                .code("f213")
                .massTheory(-1.)
                .bankaEmptyMass(1.)
                .build();

        ResponseEntity<String> response = testRestTemplate.postForEntity("/probes", dto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_ok_withoutResearches() {
        Probe entity = repository.save(new ProbeFactory().createSimple());
        ProbeUpdateDto dto = ProbeUpdateDto.builder()
                .name("updated name")
                .code("updated code")
                .massTheory(2.)
                .bankaEmptyMass(2.)
                .bankaWithProbeMass(3.)
                .build();

        testRestTemplate.put("/probes/{id}", dto, entity.getId());

        Probe updated = repository.findById(entity.getId()).orElseThrow();
        assertEquals(dto.name(), updated.getName());
        assertEquals(dto.code(), updated.getCode());
        assertEquals(dto.massTheory(), updated.getMassTheory());
        assertEquals(dto.bankaEmptyMass(), updated.getBankaEmptyMass());
        assertEquals(dto.bankaWithProbeMass(), updated.getBankaWithProbeMass());
    }

    @Test
    void update_ok_addNewResearches() {
        Probe entity = repository.save(new ProbeFactory().createSimple());
        FatsResearchCreateDto fatsResearchCreateDto = FatsResearchCreateDto.builder()
                .patronMassBeforeExtractionParallelFirst(1.)
                .patronMassBeforeExtractionParallelSecond(2.)
                .patronMassAfterExtractionParallelFirst(1.)
                .patronMassAfterExtractionParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .build();
        DrySubstancesResearchCreateDto drySubstancesResearchCreateDto = DrySubstancesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(2.)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .build();
        ProteinsResearchCreateDto proteinsResearchCreateDto = ProteinsResearchCreateDto.builder()
                .titrantVolumeParallelFirst(1.)
                .titrantVolumeParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .controlVolume(10.)
                .coefficient(10.)
                .build();
        ProbeUpdateDto dto = ProbeUpdateDto.builder()
                .name("updated name")
                .code("updated code")
                .massTheory(2.)
                .bankaEmptyMass(2.)
                .bankaWithProbeMass(3.)
                .fatsResearch(fatsResearchCreateDto)
                .drySubstancesResearch(drySubstancesResearchCreateDto)
                .proteinsResearch(proteinsResearchCreateDto)
                .build();

        testRestTemplate.put("/probes/{id}", dto, entity.getId());

        Probe updated = repository.findById(entity.getId()).orElseThrow();
        assertEquals(dto.name(), updated.getName());
        assertEquals(dto.code(), updated.getCode());
        assertEquals(dto.massTheory(), updated.getMassTheory());
        assertEquals(dto.bankaEmptyMass(), updated.getBankaEmptyMass());
        assertEquals(dto.bankaWithProbeMass(), updated.getBankaWithProbeMass());

        assertNotNull(updated.getFatsResearch());
        assertNotNull(updated.getDrySubstancesResearch());
        assertNotNull(updated.getProteinsResearch());

        assertEquals(updated.getFatsResearch().getPatronMassBeforeExtractionParallelFirst(), fatsResearchCreateDto.patronMassBeforeExtractionParallelFirst());
        assertEquals(updated.getFatsResearch().getPatronMassBeforeExtractionParallelSecond(), fatsResearchCreateDto.patronMassBeforeExtractionParallelSecond());
        assertEquals(updated.getFatsResearch().getPatronMassAfterExtractionParallelFirst(), fatsResearchCreateDto.patronMassAfterExtractionParallelFirst());
        assertEquals(updated.getFatsResearch().getPatronMassAfterExtractionParallelSecond(), fatsResearchCreateDto.patronMassAfterExtractionParallelSecond());
        assertEquals(updated.getFatsResearch().getMassNaveskiParallelFirst(), fatsResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(updated.getFatsResearch().getMassNaveskiParallelSecond(), fatsResearchCreateDto.massNaveskiParallelSecond());

        assertEquals(updated.getDrySubstancesResearch().getByuksaParallelFirst(), drySubstancesResearchCreateDto.byuksaParallelFirst());
        assertEquals(updated.getDrySubstancesResearch().getByuksaParallelSecond(), drySubstancesResearchCreateDto.byuksaParallelSecond());
        assertEquals(updated.getDrySubstancesResearch().getByuksaAfterDryingParallelFirst(), drySubstancesResearchCreateDto.byuksaAfterDryingParallelFirst());
        assertEquals(updated.getDrySubstancesResearch().getByuksaAfterDryingParallelSecond(), drySubstancesResearchCreateDto.byuksaAfterDryingParallelSecond());
        assertEquals(updated.getDrySubstancesResearch().getMassNaveskiParallelFirst(), drySubstancesResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(updated.getDrySubstancesResearch().getMassNaveskiParallelSecond(), drySubstancesResearchCreateDto.massNaveskiParallelSecond());

        assertEquals(updated.getProteinsResearch().getTitrantVolumeParallelFirst(), proteinsResearchCreateDto.titrantVolumeParallelFirst());
        assertEquals(updated.getProteinsResearch().getTitrantVolumeParallelSecond(), proteinsResearchCreateDto.titrantVolumeParallelSecond());
        assertEquals(updated.getProteinsResearch().getMassNaveskiParallelFirst(), proteinsResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(updated.getProteinsResearch().getMassNaveskiParallelSecond(), proteinsResearchCreateDto.massNaveskiParallelSecond());
        assertEquals(updated.getProteinsResearch().getControlVolume(), proteinsResearchCreateDto.controlVolume());
        assertEquals(updated.getProteinsResearch().getCoefficient(), proteinsResearchCreateDto.coefficient());
    }

    @Test
    void update_ok_updateExistingResearch() {
        Probe entity = repository.save(new ProbeFactory().createSimple());
        FatsResearch fatsResearch = new FatsResearchFactory().createSimple(entity);
        DrySubstancesResearch drySubstancesResearch = new DrySubstancesResearchFactory().createSimple(entity);
        ProteinsResearch proteinsResearch = new ProteinsResearchFactory().createSimple(entity);
        entity.setFatsResearch(fatsResearch);
        entity.setProteinsResearch(proteinsResearch);
        entity.setDrySubstancesResearch(drySubstancesResearch);
        FatsResearchCreateDto fatsResearchCreateDto = FatsResearchCreateDto.builder()
                .patronMassBeforeExtractionParallelFirst(1.)
                .patronMassBeforeExtractionParallelSecond(2.)
                .patronMassAfterExtractionParallelFirst(1.)
                .patronMassAfterExtractionParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .build();
        DrySubstancesResearchCreateDto drySubstancesResearchCreateDto = DrySubstancesResearchCreateDto.builder()
                .byuksaParallelFirst(1.)
                .byuksaParallelSecond(2.)
                .byuksaAfterDryingParallelFirst(10.)
                .byuksaAfterDryingParallelSecond(10.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .build();
        ProteinsResearchCreateDto proteinsResearchCreateDto = ProteinsResearchCreateDto.builder()
                .titrantVolumeParallelFirst(1.)
                .titrantVolumeParallelSecond(2.)
                .massNaveskiParallelFirst(10.0)
                .massNaveskiParallelSecond(10.0)
                .controlVolume(10.)
                .coefficient(10.)
                .build();
        ProbeUpdateDto dto = ProbeUpdateDto.builder()
                .name("updated name")
                .code("updated code")
                .massTheory(2.)
                .bankaEmptyMass(2.)
                .bankaWithProbeMass(3.)
                .fatsResearch(fatsResearchCreateDto)
                .drySubstancesResearch(drySubstancesResearchCreateDto)
                .proteinsResearch(proteinsResearchCreateDto)
                .build();

        testRestTemplate.put("/probes/{id}", dto, entity.getId());

        Probe updated = repository.findById(entity.getId()).orElseThrow();
        assertEquals(dto.name(), updated.getName());
        assertEquals(dto.code(), updated.getCode());
        assertEquals(dto.massTheory(), updated.getMassTheory());
        assertEquals(dto.bankaEmptyMass(), updated.getBankaEmptyMass());
        assertEquals(dto.bankaWithProbeMass(), updated.getBankaWithProbeMass());

        assertNotNull(updated.getFatsResearch());
        assertNotNull(updated.getDrySubstancesResearch());
        assertNotNull(updated.getProteinsResearch());

        assertEquals(updated.getFatsResearch().getPatronMassBeforeExtractionParallelFirst(), fatsResearchCreateDto.patronMassBeforeExtractionParallelFirst());
        assertEquals(updated.getFatsResearch().getPatronMassBeforeExtractionParallelSecond(), fatsResearchCreateDto.patronMassBeforeExtractionParallelSecond());
        assertEquals(updated.getFatsResearch().getPatronMassAfterExtractionParallelFirst(), fatsResearchCreateDto.patronMassAfterExtractionParallelFirst());
        assertEquals(updated.getFatsResearch().getPatronMassAfterExtractionParallelSecond(), fatsResearchCreateDto.patronMassAfterExtractionParallelSecond());
        assertEquals(updated.getFatsResearch().getMassNaveskiParallelFirst(), fatsResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(updated.getFatsResearch().getMassNaveskiParallelSecond(), fatsResearchCreateDto.massNaveskiParallelSecond());

        assertEquals(updated.getDrySubstancesResearch().getByuksaParallelFirst(), drySubstancesResearchCreateDto.byuksaParallelFirst());
        assertEquals(updated.getDrySubstancesResearch().getByuksaParallelSecond(), drySubstancesResearchCreateDto.byuksaParallelSecond());
        assertEquals(updated.getDrySubstancesResearch().getByuksaAfterDryingParallelFirst(), drySubstancesResearchCreateDto.byuksaAfterDryingParallelFirst());
        assertEquals(updated.getDrySubstancesResearch().getByuksaAfterDryingParallelSecond(), drySubstancesResearchCreateDto.byuksaAfterDryingParallelSecond());
        assertEquals(updated.getDrySubstancesResearch().getMassNaveskiParallelFirst(), drySubstancesResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(updated.getDrySubstancesResearch().getMassNaveskiParallelSecond(), drySubstancesResearchCreateDto.massNaveskiParallelSecond());

        assertEquals(updated.getProteinsResearch().getTitrantVolumeParallelFirst(), proteinsResearchCreateDto.titrantVolumeParallelFirst());
        assertEquals(updated.getProteinsResearch().getTitrantVolumeParallelSecond(), proteinsResearchCreateDto.titrantVolumeParallelSecond());
        assertEquals(updated.getProteinsResearch().getMassNaveskiParallelFirst(), proteinsResearchCreateDto.massNaveskiParallelFirst());
        assertEquals(updated.getProteinsResearch().getMassNaveskiParallelSecond(), proteinsResearchCreateDto.massNaveskiParallelSecond());
        assertEquals(updated.getProteinsResearch().getControlVolume(), proteinsResearchCreateDto.controlVolume());
        assertEquals(updated.getProteinsResearch().getCoefficient(), proteinsResearchCreateDto.coefficient());
    }

    @Test
    void update_ok_removeExistingResearch() {
        Probe entity = repository.save(new ProbeFactory().createSimple());
        FatsResearch fatsResearch = new FatsResearchFactory().createSimple(entity);
        DrySubstancesResearch drySubstancesResearch = new DrySubstancesResearchFactory().createSimple(entity);
        ProteinsResearch proteinsResearch = new ProteinsResearchFactory().createSimple(entity);
        entity.setFatsResearch(fatsResearch);
        entity.setProteinsResearch(proteinsResearch);
        entity.setDrySubstancesResearch(drySubstancesResearch);
        ProbeUpdateDto dto = ProbeUpdateDto.builder()
                .name("updated name")
                .code("updated code")
                .massTheory(2.)
                .bankaEmptyMass(2.)
                .bankaWithProbeMass(3.)
                .build();

        testRestTemplate.put("/probes/{id}", dto, entity.getId());

        Probe updated = repository.findById(entity.getId()).orElseThrow();
        assertEquals(dto.name(), updated.getName());
        assertEquals(dto.code(), updated.getCode());
        assertEquals(dto.massTheory(), updated.getMassTheory());
        assertEquals(dto.bankaEmptyMass(), updated.getBankaEmptyMass());
        assertEquals(dto.bankaWithProbeMass(), updated.getBankaWithProbeMass());
        assertNull(updated.getFatsResearch());
        assertNull(updated.getDrySubstancesResearch());
        assertNull(updated.getProteinsResearch());
    }

    @Test
    void delete_ok() {
        Probe entity = repository.save(new ProbeFactory().createSimple());
        FatsResearch fatsResearch = new FatsResearchFactory().createSimple(entity);
        DrySubstancesResearch drySubstancesResearch = new DrySubstancesResearchFactory().createSimple(entity);
        ProteinsResearch proteinsResearch = new ProteinsResearchFactory().createSimple(entity);
        entity.setFatsResearch(fatsResearch);
        entity.setProteinsResearch(proteinsResearch);
        entity.setDrySubstancesResearch(drySubstancesResearch);

        testRestTemplate.delete("/probes/{id}", entity.getId());

        assertFalse(repository.findById(entity.getId()).isPresent());
        assertFalse(fatsResearchRepository.findById(fatsResearch.getId()).isPresent());
        assertFalse(drySubstancesResearchRepository.findById(drySubstancesResearch.getId()).isPresent());
        assertFalse(proteinsResearchRepository.findById(proteinsResearch.getId()).isPresent());
    }
}