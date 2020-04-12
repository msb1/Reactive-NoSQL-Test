package com.toptech.cassTest.service;

import com.toptech.cassTest.model.Equipment;
import com.toptech.cassTest.repository.EquipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EquipService {

    private static Logger logger = LoggerFactory.getLogger(EquipService.class);

    @Autowired
    private EquipRepository repo;

    public Mono<Equipment> createEquipment(Equipment equip) {
        return repo.insert(equip);
    }

    public Mono<Equipment> updateEquipment(Equipment equip) {
        return repo.save(equip);
    }

    public Mono<Equipment> getEquipment(String name) {
        return repo.findById(name);
    }

    public Flux<Equipment> getAllEquipment() {
        return repo.findAll();
    }

    public Mono<Void> deleteEquipment(String name) {
        return repo.deleteById(name);
    }

}
