package com.toptech.mongoTest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(reactiveMongoTemplateRef = "dataMongoTemplate")
public class DataMongoConfig {
}
