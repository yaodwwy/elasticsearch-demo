package demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @Bean
    public CommandLineRunner init() {
        return (args) -> {
            System.out.println("@RestController init");
        };
    }
}
