package com.bootstrap.jimas.config;

import java.net.UnknownHostException;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClientOptions;

//@Configuration
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
//@ComponentScan
//@EnableConfigurationProperties(MongoProperties.class)
public class MongoDBConfiguration extends AbstractMongoConfiguration{
	@Autowired
	private MongoProperties properties;
	@Autowired(required = false)
	private MongoClientOptions options;
	private Mongo mongo;
	@Autowired
    private Environment env;
	@PreDestroy
	public void close() {
		if (this.mongo != null) {
			this.mongo.close();
		}
	}
	@Bean
	public Mongo mongo() throws UnknownHostException {
		this.mongo = this.properties.createMongoClient(this.options,env);
		return this.mongo;
	}
    @Override
    protected String getDatabaseName() {
        // TODO Auto-generated method stub
        return env.getProperty("spring.data.mongodb.database");
    }
}