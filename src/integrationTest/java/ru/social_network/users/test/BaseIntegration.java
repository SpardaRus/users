package ru.social_network.users.test;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.social_network.users.repository.SubscriptionRepository;
import ru.social_network.users.repository.UserRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseIntegration {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @AfterEach
    void tearDown() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected ResultActions post(String url, String requestBody) throws Exception {
        return perform(requestBody, MockMvcRequestBuilders.post(url));
    }

    private ResultActions perform(String requestBody, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(
                        requestBuilder
                                .contentType(APPLICATION_JSON_VALUE)
                                .accept(APPLICATION_JSON_VALUE)
                                .content(requestBody)
                )
                .andDo(print());
    }
}
