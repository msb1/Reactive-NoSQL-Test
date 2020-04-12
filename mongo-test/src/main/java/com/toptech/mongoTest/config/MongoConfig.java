package com.toptech.mongoTest.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
public class MongoConfig {

    private String infoURI;
    private String dataURI;

    public MongoConfig(@Value("${spring.data.mongodb.uri}") String infoURI,
                       @Value("${mongodb.datadb.uri}") String dataURI) {
        this.infoURI = infoURI;
        this.dataURI = dataURI;
    }

    @Primary
    @Bean(name = "infoMongoTemplate")
    public ReactiveMongoTemplate infoMongoTemplate() {
        return new ReactiveMongoTemplate(new SimpleReactiveMongoDatabaseFactory(new ConnectionString(infoURI)));
    }

    @Bean(name = "dataMongoTemplate")
    public ReactiveMongoTemplate dataMongoTemplate() {
        return new ReactiveMongoTemplate(new SimpleReactiveMongoDatabaseFactory(new ConnectionString(dataURI)));
    }

}

