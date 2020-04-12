package com.toptech.cassTest.repository;

import com.toptech.cassTest.model.DataRecord;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface DataRepository extends ReactiveCassandraRepository<DataRecord, String> {
}
