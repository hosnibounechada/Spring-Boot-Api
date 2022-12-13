package com.hosnibounechada.customer;

import com.hosnibounechada.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        customerRepository.save(customer);
    }
    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable("id") Integer id){
        customerRepository.deleteById(id);
    }
    @PutMapping("{id}")
    public void updateCustomer(@PathVariable("id") Integer id,
                               @RequestBody NewCustomerRequest request){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "customer with id [%s] not found".formatted(id)
        ));

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        customerRepository.save(customer);
    }
}
