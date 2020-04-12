package com.toptech.cassTest.repository;

import com.toptech.cassTest.model.Equipment;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface EquipRepository extends ReactiveCassandraRepository<Equipment, String> {
}
