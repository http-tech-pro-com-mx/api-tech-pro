package com.tech.pro.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.pro.backend.apirest.models.entity.Area;



public interface IAreaDao extends JpaRepository<Area, Long>{

}
