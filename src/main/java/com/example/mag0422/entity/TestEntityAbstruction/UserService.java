package com.example.mag0422.entity.TestEntityAbstruction;

import java.util.Optional;

public class UserService extends AbstractService<UserA, UserRepository>{

    public UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public Optional<UserA> save(UserA entity) {
        return Optional.empty();
    }
}
