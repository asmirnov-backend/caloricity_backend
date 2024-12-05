package ru.caloricity.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.common.exception.CascadeDeleteRestrictException;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class IngredientServiceUnitTests {
    @Mock
    private IngredientRepository repository;
    @Mock
    private IngredientMapper mapper;
    @InjectMocks
    private IngredientService service;

    private UUID ingredientId;
    private Ingredient ingredient;
    private IngredientDto ingredientDto;
    private IngredientCreateDto createDto;
    private IngredientUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        ingredientId = UUID.randomUUID();
        ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredientDto = mock(IngredientDto.class);
        createDto = mock(IngredientCreateDto.class);
        updateDto = mock(IngredientUpdateDto.class);
    }

    @Test
    void findAll_withSearchParam() {
        service.findAll(PageRequest.of(1,1), "some search");
        verify(repository).findAllByNameLikeIgnoreCaseOrderByName(any(Pageable.class), anyString());
    }

    @Test
    void findById_shouldReturnOptionalIngredient() {
        when(repository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Optional<Ingredient> result = service.findById(ingredientId);

        assertThat(result).isPresent().contains(ingredient);
    }

    @Test
    void findAll_shouldReturnPageOfIngredients() {
        Pageable pageable = PageRequest.of(0, 10);
        List<IngredientInPageDto> ingredients = Collections.singletonList(mock(IngredientInPageDto.class));
        Page<IngredientInPageDto> page = new PageImpl<>(ingredients, pageable, ingredients.size());

        when(repository.findAllDtoByOrderByName(pageable)).thenReturn(page);

        Page<IngredientInPageDto> result = service.findAll(pageable, null);

        assertThat(result).isEqualTo(page);
    }

    @Test
    void findAll_withSearch_shouldReturnPageOfIngredients() {
        Pageable pageable = PageRequest.of(0, 10);
        String search = "test";
        List<IngredientInPageDto> ingredients = Collections.singletonList(mock(IngredientInPageDto.class));
        Page<IngredientInPageDto> page = new PageImpl<>(ingredients, pageable, ingredients.size());

        when(repository.findAllByNameLikeIgnoreCaseOrderByName(pageable, "%" + search + "%")).thenReturn(page);

        Page<IngredientInPageDto> result = service.findAll(pageable, search);

        assertThat(result).isEqualTo(page);
    }

    @Test
    void findDtoByIdOrThrow_shouldReturnIngredientDto() {
        when(repository.findDtoById(ingredientId)).thenReturn(Optional.of(ingredientDto));

        IngredientDto result = service.findDtoByIdOrThrow(ingredientId);

        assertThat(result).isEqualTo(ingredientDto);
    }

    @Test
    void findDtoByIdOrThrow_shouldThrowEntityNotFoundException() {
        when(repository.findDtoById(ingredientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findDtoByIdOrThrow(ingredientId));
    }

    @Test
    void getExistingReferenceByIdOrThrow_shouldReturnIngredient() {
        when(repository.existsById(ingredientId)).thenReturn(true);
        when(repository.getReferenceById(ingredientId)).thenReturn(ingredient);

        Ingredient result = service.getExistingReferenceByIdOrThrow(ingredientId);

        assertThat(result).isEqualTo(ingredient);
    }

    @Test
    void getExistingReferenceByIdOrThrow_shouldThrowEntityNotFoundException() {
        when(repository.existsById(ingredientId)).thenReturn(false);

        assertThatThrownBy(() -> service.getExistingReferenceByIdOrThrow(ingredientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(ingredientId.toString());
    }

    @Test
    void create_shouldReturnIdDto() {
        when(mapper.toEntity(createDto)).thenReturn(ingredient);
        when(repository.save(ingredient)).thenReturn(ingredient);

        IdDto result = service.create(createDto);

        assertNotEquals(result.id(), ingredientId);
    }

    @Test
    void update_shouldUpdateIngredient() {
        when(repository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
        when(repository.save(ingredient)).thenReturn(ingredient);

        service.update(ingredientId, updateDto);

        verify(mapper).updateEntity(ingredient, updateDto);
        verify(repository).save(ingredient);
    }

    @Test
    void update_shouldThrowEntityNotFoundException() {
        when(repository.findById(ingredientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(ingredientId, updateDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(ingredientId.toString());
    }

    @Test
    void deleteById_shouldDeleteIngredient() {
        when(repository.existsByIdAndProbeIngredientsIsNotEmpty(ingredientId)).thenReturn(false);

        service.deleteById(ingredientId);

        verify(repository).deleteById(ingredientId);
    }

    @Test
    void deleteById_shouldThrowCascadeDeleteRestrictException() {
        when(repository.existsByIdAndProbeIngredientsIsNotEmpty(ingredientId)).thenReturn(true);

        assertThatThrownBy(() -> service.deleteById(ingredientId))
                .isInstanceOf(CascadeDeleteRestrictException.class)
                .hasMessageContaining(ingredientId.toString());
    }

}
