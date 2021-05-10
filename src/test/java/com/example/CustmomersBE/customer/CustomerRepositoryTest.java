package com.example.CustmomersBE.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static java.time.Month.APRIL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void checkIfCanFindCustomerByUsername() {
        // given
        Customer customer = new Customer(
                "Martin",
                "Magi",
                LocalDate.of(1993, APRIL, 7),
                "MegaMartin",
                "salasona"
        );
        repository.save(customer);
        // when
        boolean expected = repository.findCustomerByUserName("MegaMartin").isPresent();

        // then
        assertThat(expected).isTrue();
    }
    @Test
    void checkIfUsernameDoesNotExists() {
        // given
        String username = "MegaMartin";
        // when
        boolean expected = repository.findCustomerByUserName(username).isPresent();

        // then
        assertThat(expected).isFalse();
    }
    @Test
    void checkIfCanDeleteCustomer() {
        // given
        Customer customer = new Customer(
                "Martin",
                "Magi",
                LocalDate.of(1993, APRIL, 7),
                "MegaMartin",
                "salasona"
        );
        repository.save(customer);
        // when
        repository.deleteById(1L);
        boolean expected = repository.findCustomerByUserName("MegaMartin").isPresent();

        // then
        assertThat(expected).isFalse();
    }
}
