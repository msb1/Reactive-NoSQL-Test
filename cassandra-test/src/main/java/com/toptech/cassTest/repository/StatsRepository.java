package com.toptech.cassTest.repository;

import com.toptech.cassTest.model.Stats;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface StatsRepository extends ReactiveCassandraRepository<Stats, String> {
}
