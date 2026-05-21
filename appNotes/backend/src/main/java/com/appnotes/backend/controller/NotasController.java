package com.appnotes.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appnotes.backend.model.NotasEntity;
import com.appnotes.backend.service.NotasService;
import com.appnotes.backend.dto.NotasData;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/apiNotas")
public class NotasController {

    private final NotasService notasService;

    public NotasController(NotasService notasService) {
        this.notasService = notasService;
    }

@PostMapping
@Valid
public ResponseEntity<NotasEntity> createNota(@RequestBody NotasData nota) {
    NotasEntity savedNota = notasService.createNota(nota);
    return ResponseEntity.ok(savedNota);
}



}
