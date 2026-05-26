package com.appnotes.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appnotes.backend.exception.NotasNotFoundException;
import com.appnotes.backend.dto.NotasData;
import com.appnotes.backend.model.NotasEntity;
import com.appnotes.backend.repository.NotasRepository;

@Service
public class NotasService {

    private final NotasRepository notasRepository;

    public NotasService(NotasRepository notasRepository) {
        this.notasRepository = notasRepository;
    }

    // post
    public NotasEntity createNota(NotasData nota) {
        NotasEntity newNota = NotasEntity.builder()
                .titulo(nota.getTitulo())
                .descripcion(nota.getDescripcion())
                .build();
        return notasRepository.save(newNota);
    }

    // get
    public NotasEntity getNotaById(Long id) {
        return notasRepository.findById(id)
                .orElseThrow(() -> new NotasNotFoundException(id));
    }

    // getAll
    public List<NotasEntity> getAllNotas() {
        return notasRepository.findAll();
    }

    // delete
    public void deleteNotaById(Long id) {
        NotasEntity existingNota = notasRepository.findById(id)
                .orElseThrow(() -> new NotasNotFoundException(id));
        notasRepository.delete(existingNota);
    }

    // update
    public NotasEntity updateNota(Long id, NotasData nota) {
        NotasEntity existingNota = notasRepository.findById(id)
                .orElseThrow(() -> new NotasNotFoundException(id));
        existingNota.setTitulo(nota.getTitulo());
        existingNota.setDescripcion(nota.getDescripcion());
        return notasRepository.save(existingNota);
    }

}
