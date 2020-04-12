package com.toptech.cassTest.service;

import com.toptech.cassTest.model.User;
import com.toptech.cassTest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repo;

    public Mono<User> createUser(User user) {
        return repo.insert(user);
    }

    public Mono<User> updateUser(User user) {
        return repo.save(user);
    }

    public Mono<User> getUser(String username) {
        return repo.findById(username);
    }

    public Flux<User> getAllUsers() {
        return repo.findAll();
    }

    public Mono<Void> deleteUser(String username) {
        return repo.deleteById(username);
    }

    public Mono<Void> deleteAll() {
        return repo.deleteAll();
    }

}
