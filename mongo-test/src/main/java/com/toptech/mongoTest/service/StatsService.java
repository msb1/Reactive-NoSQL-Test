package com.toptech.mongoTest.service;

import com.toptech.mongoTest.model.Stats;
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
public class StatsService {

    private static Logger logger = LoggerFactory.getLogger(StatsService.class);

    @Autowired
    @Qualifier("infoMongoTemplate")
    private ReactiveMongoTemplate template;

    public Mono<Stats> createStats(Stats stats, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("equipmentName").is(stats.getEquipmentName()));
        return template.exists(query, Stats.class, collectionName).flatMap((exist) -> {
            if (exist) {
                return Mono.just(stats);
            } else {
                Mono<Stats> results = template.insert(stats, collectionName);
                return results;
            }
        });
    }

    public Mono<Stats> updateStats(Stats stats, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("equipmentName").is(stats.getEquipmentName()));
        return template.exists(query, Stats.class, collectionName).flatMap((exist) -> {
            if (exist) {
                return template.findAndReplace(query, stats, collectionName);
            } else {
                return template.insert(stats, collectionName);
            }
        });
    }

    public Mono<Stats> getStats(String equipmentName, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("equipmentName").is(equipmentName));
        return template.findOne(query, Stats.class, collectionName);
    }

    public Flux<Stats> getAllStats(String collectionName) {
        return template.findAll(Stats.class, collectionName);
    }

    public Mono<Void> deleteStats(String equipmentName, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("equipmentName").is(equipmentName));
        template.remove(query, collectionName).subscribe();
        return Mono.empty();
    }

//    public Mono<Void> deleteAll(String collectionName) {
//        template.dropCollection(collectionName);
//        template.getCollection(collectionName);
//        return Mono.empty();
//    }

}
