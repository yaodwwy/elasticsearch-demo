package demo;

import demo.doc.PersonIndex;
import demo.doc.SynoIndex;
import demo.entity.Detail;
import demo.entity.Orders;
import demo.entity.OrdersRepo;
import demo.repo.PersonRepository;
import demo.repo.SynoIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yaodw
 */
@SpringBootApplication
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

    @Bean
    public CommandLineRunner initElasticsearchApplication(ElasticsearchOperations operations) {
        return (args) -> {
            System.out.println("ElasticsearchApplication bean");
        };
    }
}

