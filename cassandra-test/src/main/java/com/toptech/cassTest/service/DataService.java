package com.toptech.cassTest.service;

import com.toptech.cassTest.model.DataRecord;
import com.toptech.cassTest.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataService {

    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private DataRepository repo;

    public Mono<DataRecord> createDataRecord(DataRecord record) {
        return repo.insert(record);
    }

    public Mono<DataRecord> getDataRecord(String uuid) {
        return repo.findById(uuid);
    }

    public Flux<DataRecord> getAllDataRecords() {
        return repo.findAll();
    }

    public Mono<Void> deleteDataRecord(String uuid) {
        return repo.deleteById(uuid);
    }

    public Mono<Void> deleteAll() {
        return repo.deleteAll();
    }

}

