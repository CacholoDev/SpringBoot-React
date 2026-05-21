package com.appnotes.backend.service;

import org.springframework.stereotype.Service;

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
        return notasRepository.findById(id).orElse(null);
    }

    // getAll
    public Iterable<NotasEntity> getAllNotas() {
        return notasRepository.findAll();
    }

    // delete
    public void deleteNotaById(Long id) {
        notasRepository.deleteById(id);
    }

    // update
    public NotasEntity updateNota(Long id, NotasData nota) {
        NotasEntity existingNota = notasRepository.findById(id).orElse(null);
        if (existingNota != null) {
            existingNota.setTitulo(nota.getTitulo());
            existingNota.setDescripcion(nota.getDescripcion());
            return notasRepository.save(existingNota);
        }
        return null;
    }

}
