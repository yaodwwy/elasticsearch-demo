package demo;

import demo.entity.OrdersRepo;
import demo.repo.SynoIndexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @Bean
    public CommandLineRunner initRootController(ElasticsearchOperations operations, OrdersRepo ordersRepo, SynoIndexRepository synoIndexRepo) {
        return (args) -> {
            System.out.println("RootController bean");
        };
    }
}
