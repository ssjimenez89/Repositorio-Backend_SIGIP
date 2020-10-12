package cu.uci.cegel.onei.sigipbase;

import cu.uci.cegel.base.integration.messaging.annotations.EnableKafkaLogger;
import cu.uci.cegel.security.annotations.EnableOauth2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableOauth2
@EnableKafkaLogger
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class SigipBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigipBaseApplication.class, args);
    }
}
