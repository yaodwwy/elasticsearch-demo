package demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ServiceBean {

    @Bean
    public CommandLineRunner initServiceBean() {
        return (args) -> {
            System.out.println("ServiceBean bean");
        };
    }
}
