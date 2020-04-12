package com.toptech.cassTest.repository;

import com.toptech.cassTest.model.User;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface UserRepository extends ReactiveCassandraRepository<User, String> {
}
