package com.example.mag0422.entity.TestEntityAbstruction;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService<E extends AbstructEntity, R extends CommonRepository<E>>
        implements CommonService<E> {
    protected final R repository;
    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }
}
