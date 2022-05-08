package kimoror.siam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories(basePackages = "kimoror.siam.repositories.mongo")
@EnableJpaRepositories(basePackages = "kimoror.siam.repositories.jpa")
public class SIAM {
    public static void main(String[] args){
        SpringApplication.run(SIAM.class, args);
    }
}