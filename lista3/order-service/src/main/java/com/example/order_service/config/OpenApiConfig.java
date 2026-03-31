package com.example.order_service.config;

import com.example.order_service.client.ApiClient;
import com.example.order_service.client.api.OrderHistoryApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenApiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ApiClient apiClient(RestTemplate restTemplate) {
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath("http://localhost:8081"); // Wskazujemy na drugi mikroserwis
        return apiClient;
    }

    @Bean
    public OrderHistoryApi orderHistoryApi(ApiClient apiClient) {
        return new OrderHistoryApi(apiClient);
    }
}
