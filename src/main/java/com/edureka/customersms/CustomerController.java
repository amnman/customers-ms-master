package com.edureka.customersms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v4")
public class CustomerController {
    WebClient webClient= WebClient.create("http://localhost:8090/api/v1/book");

    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("/issueBooks")
    public ResponseEntity<String> issueBooks(@RequestHeader("Authorization") String authorization,@RequestBody Customer customer){
        boolean resp= Boolean.TRUE.equals(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{customer.getIsbn()}")
                        .build(customer.getIsbn()))
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(e -> Mono.empty())
                .block());
        if (resp){
            List<Customer> list=new ArrayList<>();
            list= customerRepository.findByCid(customer.getCid());
            Customer customerDetails=list.stream().filter(a-> a.getIsbn()==null || a.getIsbn()==0).findAny().orElse(null);
            if(customerDetails !=null){
                customerDetails.setIsbn(customer.getIsbn());
                //write update query
                customerRepository.setIsbn(customerDetails.getIsbn(),customerDetails.getCid());

            }
            else{
                customerRepository.save(customer);
            }
            return ResponseEntity.ok().body("Book Issued to Customer");

        }
        else {
            return ResponseEntity.ok().body("No copies available");
        }
    }


    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
        if (customer!=null){
            customerRepository.save(customer);
            return ResponseEntity.ok().body("Customer Added Successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Error adding Customer");
        }
    }


    @GetMapping("/customerDetails")
    public ResponseEntity<List<Customer>> fetchCustomer(){
        return ResponseEntity.ok().body(customerRepository.findAll());
    }



//    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer){
//        Optional <Customer> CustomerDb = customerRepository.findById(customer.getId());
//        if (CustomerDb.isPresent()){
//            Customer customerUpdate = CustomerDb.get();
//            customerUpdate.setSlno(customer.getSlno());
//            customerUpdate.setName(customer.getName());
//            customerUpdate.setEmail(customer.getEmail());
//            customerUpdate.setIsbn(customerUpdate.getIsbn());
//        }
//    }
}
