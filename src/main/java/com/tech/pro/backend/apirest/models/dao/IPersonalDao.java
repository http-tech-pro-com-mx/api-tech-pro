package com.tech.pro.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.pro.backend.apirest.models.entity.Personal;

public interface IPersonalDao extends JpaRepository<Personal, Long>{

}
