package com.example.mag0422.entity.TestEntityAbstruction;

import java.util.Optional;

public interface CommonService <E extends AbstructEntity>{
    Optional<E> save(E entity);
}
