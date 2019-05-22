package com.tech.pro.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.pro.backend.apirest.models.entity.Justificacion;

public interface IJustificacionDao extends JpaRepository<Justificacion, Long> {

}
