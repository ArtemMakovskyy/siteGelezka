package com.example.mag0422.dao.dao_parts.repository;

import com.example.mag0422.entity.parts.FunDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FunDBRepository extends JpaRepository<FunDB, Long> {

}
