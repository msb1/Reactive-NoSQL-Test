package com.toptech.mongoTest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import javax.annotation.PostConstruct;

@Configuration
@EnableReactiveMongoRepositories(reactiveMongoTemplateRef = "infoMongoTemplate")
public class InfoMongoConfig {

    @Autowired
    @Qualifier("infoMongoTemplate")
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @PostConstruct
    public void initIndexes() {
        reactiveMongoTemplate.indexOps("users") // collection name string or .class
                .ensureIndex(
                        new Index().on("name", Sort.Direction.ASC)
                );
    }
}
