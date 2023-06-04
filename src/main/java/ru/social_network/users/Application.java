package ru.social_network.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.social_network.users.annotaion.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
