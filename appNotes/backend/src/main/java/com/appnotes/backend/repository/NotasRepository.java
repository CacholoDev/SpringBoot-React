package com.appnotes.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appnotes.backend.model.NotasEntity;

public interface NotasRepository extends JpaRepository<NotasEntity, Long> {
    
}
