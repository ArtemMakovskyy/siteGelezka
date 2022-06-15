package com.example.mag0422.dao.dao_parts.repository;

import com.example.mag0422.entity.parts.SSD_DB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface SSD_DBRepository extends JpaRepository<SSD_DB, Long> {
}