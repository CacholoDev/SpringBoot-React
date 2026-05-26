package com.appnotes.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appnotes.backend.dto.NotasData;
import com.appnotes.backend.model.NotasEntity;
import com.appnotes.backend.service.NotasService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/notas")
public class NotasController {

    private final NotasService notasService;

    public NotasController(NotasService notasService) {
        this.notasService = notasService;
    }

    @PostMapping
    public ResponseEntity<NotasEntity> createNota(@Valid @RequestBody NotasData nota) {
        NotasEntity savedNota = notasService.createNota(nota);
        return ResponseEntity.status(201).body(savedNota);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotasEntity> getNotaById(@PathVariable Long id) {
        return ResponseEntity.ok(notasService.getNotaById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotasEntity>> getAllNotas() {
        List<NotasEntity> notas = notasService.getAllNotas();
        return ResponseEntity.ok(notas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotaById(@PathVariable Long id) {
        notasService.deleteNotaById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotasEntity> updateNota(@PathVariable Long id, @Valid @RequestBody NotasData nota) {
        return ResponseEntity.ok(notasService.updateNota(id, nota));
    }

}
