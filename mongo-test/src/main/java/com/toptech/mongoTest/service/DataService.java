package com.toptech.mongoTest.service;

import com.toptech.mongoTest.model.DataRecord;
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
public class DataService {

    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    @Qualifier("dataMongoTemplate")
    private ReactiveMongoTemplate template;

    public Mono<DataRecord> createDataRecord(DataRecord record, String collectionName) {
        try {
            return template.insert(record, collectionName);
        } catch (Exception ex) {
            return Mono.just(record);
        }
    }

    public Mono<DataRecord> getDataRecord(String uuid, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(uuid));
        return template.findOne(query, DataRecord.class, collectionName);
    }

    public Flux<DataRecord> getAllDataRecords(String collectionName) {
        return template.findAll(DataRecord.class, collectionName);
    }

    public Mono<Void> deleteDataRecord(String uuid, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(uuid));
        template.remove(query, collectionName).subscribe();
        return Mono.empty();
    }

//    public Mono<Void> deleteAll(String collectionName) {
//        template.dropCollection(collectionName);
//        template.getCollection(collectionName);
//        return Mono.empty();
//    }

    public void createCollection(String collectionName){
        template.createCollection(collectionName);
    }

}

