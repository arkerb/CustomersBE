package com.example.CustmomersBE.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(CustomerDTO customer) {
        System.out.println("Made it");
        Optional<Customer> customerOptional = customerRepository.findCustomerByUserName(customer.getUsername());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("Username is taken");
        }
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setUsername(customer.getUsername());
        newCustomer.setPassword(customer.getPassword());
        newCustomer.setDateOfBirth(convertToLocaldate(customer.getDateOfBirth()));
        customerRepository.save(newCustomer);
    }
    private LocalDate convertToLocaldate(String date){
        String[] dateArray = date.split("-");
        Integer[] intArray = Stream.of(dateArray).map(Integer::valueOf).toArray(Integer[]::new);
        return LocalDate.of(intArray[0], intArray[1], intArray[2]);
    }

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists) {
            throw new IllegalStateException("Customer with id " + customerId + " does not exists");
        }
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId,
                               CustomerDTO customerDTO) {

        String firstName = customerDTO.getFirstName();
        String lastName = customerDTO.getLastName();
        String username = customerDTO.getUsername();
        String password = customerDTO.getPassword();
        String dateOfBirth = customerDTO.getDateOfBirth();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow( () -> new IllegalStateException("Customer with id " + customerId + "does not exists"));
        if (firstName != null &&
                firstName.length() > 0 &&
                !Objects.equals(customer.getFirstName(), firstName))
        {
            customer.setFirstName(firstName);
        }
        if (lastName != null &&
                lastName.length() > 0 &&
                !Objects.equals(customer.getLastName(), lastName)){
            customer.setLastName(lastName);
        }
        if (dateOfBirth != null){
            customer.setDateOfBirth(convertToLocaldate(dateOfBirth));
        }
        if (username != null &&
            username.length() > 0 &&
            !Objects.equals(customer.getUsername(), username)){
            customer.setUsername(username);
        }
        if (password != null &&
            password.length() > 0 &&
            !Objects.equals(customer.getPassword(), password)){
            customer.setPassword(password);
        }
    }

    public Customer getCustomerById(Long customerId) {
            return customerRepository.findById(customerId).
                    orElseThrow( () -> new IllegalStateException("Customer Id " + customerId + " does not exist"));
    }
}
