package com.toptech.mongoTest.service;

import com.toptech.mongoTest.model.Equipment;
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
public class EquipService {

    private static Logger logger = LoggerFactory.getLogger(EquipService.class);

    @Autowired
    @Qualifier("infoMongoTemplate")
    private ReactiveMongoTemplate template;

    public Mono<Equipment> createEquipment(Equipment equip, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(equip.getName()));
        return template.exists(query, Equipment.class, collectionName).flatMap((exist) -> {
            if (exist) {
                return Mono.just(equip);
            } else {
                return template.insert(equip, collectionName);
            }
        });
    }

    public Mono<Equipment> updateEquipment(Equipment equip, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(equip.getName()));
        return template.exists(query, Equipment.class, collectionName).flatMap((exist) -> {
            if (exist) {
                return template.findAndReplace(query, equip, collectionName);
            } else {
                return template.insert(equip, collectionName);
            }
        });
    }

    public Mono<Equipment> getEquipment(String name, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return template.findOne(query, Equipment.class, collectionName);
    }

    public Flux<Equipment> getAllEquipment(String collectionName) {
        return template.findAll(Equipment.class, collectionName);
    }

    public Mono<Void> deleteEquipment(String name, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        template.remove(query, collectionName).subscribe();
        return Mono.empty();
    }

//    public Mono<Void> deleteAll(String collectionName) {
//        template.dropCollection(collectionName);
//        template.getCollection(collectionName);
//        return Mono.empty();
//    }

}
