package com.appnotes.backend.model;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class NotasEntityTest {

    private Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldBuildEntityWithValues() {
        NotasEntity nota = NotasEntity.builder()
                .titulo("Mi primera nota")
                .descripcion("Descripción de prueba")
                .build();

        assertThat(nota.getTitulo()).isEqualTo("Mi primera nota");
        assertThat(nota.getDescripcion()).isEqualTo("Descripción de prueba");
    }

    @Test
    void shouldRejectBlankTitulo() {
        NotasEntity nota = NotasEntity.builder()
                .titulo(" ")
                .descripcion("Algo")
                .build();

        Set<ConstraintViolation<NotasEntity>> violations = validator().validate(nota);

        assertThat(violations)
                .anySatisfy(violation -> {
                    assertThat(violation.getPropertyPath()).hasToString("titulo");
                    assertThat(violation.getMessage()).isEqualTo("El título es obligatorio");
                });
    }
}