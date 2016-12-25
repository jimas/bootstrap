package com.bootstrap.jimas.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoProperties {
    public static final int DEFAULT_PORT = 27017;
    private String host;
    private Integer port;
    private String uri;
    private String database;
    private String authenticationDatabase;
    private String gridFsDatabase;
    private String username;
    private char[] password;
    private Class<?> fieldNamingStrategy;

    public MongoProperties() {
        this.port = null;

        this.uri = "mongodb://localhost/test";
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getAuthenticationDatabase() {
        return this.authenticationDatabase;
    }

    public void setAuthenticationDatabase(String authenticationDatabase) {
        this.authenticationDatabase = authenticationDatabase;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Class<?> getFieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }

    public void setFieldNamingStrategy(Class<?> fieldNamingStrategy) {
        this.fieldNamingStrategy = fieldNamingStrategy;
    }

    public void clearPassword() {
        if (this.password == null) {
            return;
        }
        for (int i = 0; i < this.password.length; ++i)
            this.password[i] = '\0';
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getGridFsDatabase() {
        return this.gridFsDatabase;
    }

    public void setGridFsDatabase(String gridFsDatabase) {
        this.gridFsDatabase = gridFsDatabase;
    }

    public String getMongoClientDatabase() {
        if (this.database != null) {
            return this.database;
        }
        return new MongoClientURI(this.uri).getDatabase();
    }

    public MongoClient createMongoClient(MongoClientOptions options, Environment environment) throws UnknownHostException {
        try {
            List credentials;
            if ((hasCustomAddress()) || (hasCustomCredentials())) {
                if (options == null) {
                    options = MongoClientOptions.builder().build();
                }
                credentials = new ArrayList();
                if (hasCustomCredentials()) {
                    String database = (this.authenticationDatabase == null) ? getMongoClientDatabase() : this.authenticationDatabase;
                    credentials.add(MongoCredential.createPlainCredential(this.username, database, this.password));
                    
                }

                String host = (this.host == null) ? "localhost" : this.host;
                int port = determinePort(environment);

                MongoClient localMongoClient = new MongoClient(Collections.singletonList(new ServerAddress(host, port)), credentials, options);
                return localMongoClient;
            }

            return new MongoClient(new MongoClientURI(this.uri, builder(options)));
        } finally {
            clearPassword();
        }
    }

    private boolean hasCustomAddress() {
        return ((this.host != null) || (this.port != null));
    }

    private boolean hasCustomCredentials() {
        return ((this.username != null) && (this.password != null));
    }

    private int determinePort(Environment environment) {
        if (this.port == null) {
            return 27017;
        }
        if (this.port.intValue() == 0) {
            if (environment != null) {
                String localPort = environment.getProperty("local.mongo.port");
                if (localPort != null) {
                    return Integer.valueOf(localPort).intValue();
                }
            }
            throw new IllegalStateException("spring.data.mongodb.port=0 and no local mongo port configuration is available");
        }

        return this.port.intValue();
    }

    private MongoClientOptions.Builder builder(MongoClientOptions options) {
        if (options != null) {
            return MongoClientOptions.builder(options);
        }
        return MongoClientOptions.builder();
    }
}