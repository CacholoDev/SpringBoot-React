package com.appnotes.backend.controller;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.appnotes.backend.dto.NotasData;
import com.appnotes.backend.exception.GlobalExceptionHandler;
import com.appnotes.backend.model.NotasEntity;
import com.appnotes.backend.service.NotasService;

@ExtendWith(MockitoExtension.class)
class NotasControllerTest {

    @Mock
    private NotasService notasService;

    private MockMvc mockMvc;

        private MockMvc buildMockMvc() {
                return MockMvcBuilders
                .standaloneSetup(new NotasController(notasService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnAllNotas() throws Exception {
                mockMvc = buildMockMvc();
        when(notasService.getAllNotas()).thenReturn(List.of(
                NotasEntity.builder().id(1L).titulo("A").descripcion("B").build(),
                NotasEntity.builder().id(2L).titulo("C").descripcion("D").build()));

        mockMvc.perform(get("/api/notas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo").value("A"));
    }

    @Test
    void shouldReturnNotaById() throws Exception {
                mockMvc = buildMockMvc();
        when(notasService.getNotaById(1L)).thenReturn(
                NotasEntity.builder().id(1L).titulo("A").descripcion("B").build());

        mockMvc.perform(get("/api/notas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("A"));
    }

    @Test
    void shouldCreateNota() throws Exception {
                mockMvc = buildMockMvc();
        when(notasService.createNota(any(NotasData.class))).thenReturn(
                NotasEntity.builder().id(1L).titulo("A").descripcion("B").build());

        mockMvc.perform(post("/api/notas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":"A","descripcion":"B"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("A"));
    }

    @Test
    void shouldRejectInvalidNota() throws Exception {
                mockMvc = buildMockMvc();
        mockMvc.perform(post("/api/notas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":"","descripcion":"B"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));
    }

    @Test
    void shouldUpdateNota() throws Exception {
                mockMvc = buildMockMvc();
        when(notasService.updateNota(any(Long.class), any(NotasData.class))).thenReturn(
                NotasEntity.builder().id(1L).titulo("New").descripcion("New desc").build());

        mockMvc.perform(put("/api/notas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":"New","descripcion":"New desc"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("New"));
    }

    @Test
    void shouldDeleteNota() throws Exception {
                mockMvc = buildMockMvc();
        mockMvc.perform(delete("/api/notas/1"))
                .andExpect(status().isNoContent());

        verify(notasService).deleteNotaById(1L);
    }
}