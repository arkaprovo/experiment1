package com.example.threadlocal.demo.config;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.connection.ServerSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDbConfig {

    Logger log = LoggerFactory.getLogger(MongoDbConfig.class);


    private final MongoProperties mongoProperties;

    public MongoDbConfig(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }


    // For more details refer to this site https://mongodb.github.io/mongo-java-driver/3.7/driver/tutorials/connect-to-mongodb/
    public @Bean MongoClient mongoClient() {
        String urlToDebug = String.format("mongodb://%s:%d",mongoProperties.getHost(),mongoProperties.getPort());
        log.info("mongodb URL is {}",urlToDebug);
        return MongoClients.create(urlToDebug);
    }

    public @Bean("demoDB") MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "demo-database");
    }


}
