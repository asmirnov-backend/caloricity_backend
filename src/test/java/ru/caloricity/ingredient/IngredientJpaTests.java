package ru.caloricity.ingredient;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class IngredientJpaTests {
    @Autowired
    private IngredientRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    void findAllDtoByOrderByName_correctOrder() {
        repository.deleteAll();
        var factory = new IngredientFactory();
        repository.saveAll(List.of(
                factory.createSimpleWithName("груши"),
                factory.createSimpleWithName("яблоки"),
                factory.createSimpleWithName("Бананы"),
                factory.createSimpleWithName("апельсины")
        ));

        Page<IngredientInPageDto> ingredients = repository.findAllDtoByOrderByName(Pageable.ofSize(10));

        Iterator<String> names = ingredients.get().map(IngredientInPageDto::name).toList().iterator();
        assertEquals(names.next(), "апельсины");
        assertEquals(names.next(), "Бананы");
        assertEquals(names.next(), "груши");
        assertEquals(names.next(), "яблоки");
    }

    @Test
    void findAllByNameLikeIgnoreCaseOrderByName_correctOrder() {
        repository.deleteAll();
        var factory = new IngredientFactory();
        repository.saveAll(List.of(
                factory.createSimpleWithName("кабачки"),
                factory.createSimpleWithName("баклажаны"),
                factory.createSimpleWithName("Бананы"),
                factory.createSimpleWithName("баобабы"),
                factory.createSimpleWithName("апельсины")
        ));

        Page<IngredientInPageDto> ingredients = repository.findAllByNameLikeIgnoreCaseOrderByName(Pageable.ofSize(10), "%ба%");

        Iterator<String> names = ingredients.get().map(IngredientInPageDto::name).toList().iterator();
        assertEquals(names.next(), "баклажаны");
        assertEquals(names.next(), "Бананы");
        assertEquals(names.next(), "баобабы");
        assertEquals(names.next(), "кабачки");
    }

}
