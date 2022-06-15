package com.example.mag0422.entity.TestEntityAbstruction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<E extends AbstructEntity> extends CrudRepository<E, Long>{
}
