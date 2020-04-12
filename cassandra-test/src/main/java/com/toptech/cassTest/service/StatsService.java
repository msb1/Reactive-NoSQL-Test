package com.toptech.cassTest.service;

import com.toptech.cassTest.model.Stats;
import com.toptech.cassTest.repository.StatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StatsService {

    private static Logger logger = LoggerFactory.getLogger(StatsService.class);

    @Autowired
    private StatsRepository repo;

    public Mono<Stats> createStats(Stats stats) {
        return repo.insert(stats);
    }

    public Mono<Stats> updateStats(Stats stats) {
        return repo.save(stats);
    }

    public Mono<Stats> getStats(String equipmentName) {
        return repo.findById(equipmentName);
    }

    public Flux<Stats> getAllStats() {
        return repo.findAll();
    }

    public Mono<Void> deleteStats(String equipmentName) {
        return repo.deleteById(equipmentName);
    }

}
