package com.example.mag0422.dao.dao_user;

import com.example.mag0422.entity.entity_user.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository_People extends JpaRepository<Person,Integer> {
}
