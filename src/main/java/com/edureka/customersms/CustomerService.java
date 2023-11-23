package com.edureka.customersms;

import org.springframework.web.reactive.function.client.WebClient;

public class CustomerService {
    WebClient webClient= WebClient.create("http://localhost:8090/api/v1/books");





}
