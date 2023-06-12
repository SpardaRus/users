package ru.social_network.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTests {

    @Test
    void contextLoads() {
        String actual = "main";
        assertEquals("main", actual);
    }
}
