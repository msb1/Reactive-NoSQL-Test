package com.toptech.mongoTest.service;

import com.toptech.mongoTest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    @Qualifier("infoMongoTemplate")
    private ReactiveMongoTemplate template;

    public Mono<User> createUser(User user, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        return template.exists(query, User.class, collectionName).flatMap((exist) -> {
            if (exist) {
                return template.findOne(query, User.class, collectionName);
            } else {
                return template.insert(user, collectionName);
            }
        });
    }

    public Mono<User> updateUser(User user, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        return template.exists(query, User.class).flatMap((exist) -> {
            if (exist) {
                return template.findAndReplace(query, user, collectionName);
            } else {
                return Mono.just(user);
            }
        });
    }

    public Mono<User> getUser(String username, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return template.findOne(query, User.class, collectionName);
    }

    public Flux<User> getAllUsers(String collectionName) {
        return template.findAll(User.class);
    }

    public Mono<Void> deleteUser(String username, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        template.remove(query, collectionName).subscribe();
        return Mono.empty();
    }

    public Mono<Void> deleteAll(String collectionName) {
        template.dropCollection(collectionName);
        template.getCollection(collectionName);
        return Mono.empty();
    }

}
