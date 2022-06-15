package com.example.mag0422.dao.dao_parts.repository;

import com.example.mag0422.entity.parts.PowerDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface PowerDBRepository extends JpaRepository<PowerDB, Long> {
}