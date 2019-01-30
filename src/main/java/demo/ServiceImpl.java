package demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl {

    @Bean
    public CommandLineRunner init() {
        return (args) -> {
            System.out.println("@Service Bean init");
        };
    }
}
