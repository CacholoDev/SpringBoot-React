package com.appnotes.backend.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.appnotes.backend.dto.NotasData;
import com.appnotes.backend.exception.NotasNotFoundException;
import com.appnotes.backend.model.NotasEntity;
import com.appnotes.backend.repository.NotasRepository;

@ExtendWith(MockitoExtension.class)
class NotasServiceTest {

    @Mock
    private NotasRepository notasRepository;

    @InjectMocks
    private NotasService notasService;

    @Test
    void shouldCreateNota() {
        NotasData notaData = NotasData.builder()
                .titulo("Titulo")
                .descripcion("Descripcion")
                .build();

        when(notasRepository.save(any(NotasEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotasEntity created = notasService.createNota(notaData);

        assertThat(created.getTitulo()).isEqualTo("Titulo");
        assertThat(created.getDescripcion()).isEqualTo("Descripcion");
        verify(notasRepository).save(any(NotasEntity.class));
    }

    @Test
    void shouldReturnAllNotas() {
        when(notasRepository.findAll()).thenReturn(List.of(
                NotasEntity.builder().id(1L).titulo("A").descripcion("B").build()));

        List<NotasEntity> notas = notasService.getAllNotas();

        assertThat(notas).hasSize(1);
        assertThat(notas.getFirst().getTitulo()).isEqualTo("A");
    }

    @Test
    void shouldThrowWhenNotaDoesNotExist() {
        when(notasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notasService.getNotaById(99L))
                .isInstanceOf(NotasNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldUpdateNota() {
        NotasEntity existing = NotasEntity.builder()
                .id(1L)
                .titulo("Old")
                .descripcion("Old desc")
                .build();
        NotasData update = NotasData.builder()
                .titulo("New")
                .descripcion("New desc")
                .build();

        when(notasRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(notasRepository.save(any(NotasEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotasEntity updated = notasService.updateNota(1L, update);

        assertThat(updated.getTitulo()).isEqualTo("New");
        assertThat(updated.getDescripcion()).isEqualTo("New desc");
    }

    @Test
    void shouldDeleteNota() {
        NotasEntity existing = NotasEntity.builder().id(1L).titulo("Old").descripcion("Old desc").build();
        when(notasRepository.findById(1L)).thenReturn(Optional.of(existing));

        notasService.deleteNotaById(1L);

        verify(notasRepository).delete(existing);
    }
}