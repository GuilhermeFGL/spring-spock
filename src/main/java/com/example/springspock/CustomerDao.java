package com.example.springspock;

import com.example.springspock.model.Customer;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomerDao {

    @PersistenceContext
    private final EntityManager entityManager;

    private final Logger logger;

    public CustomerDao(final EntityManager entityManager,
                       final Logger logger) {
        this.entityManager = entityManager;
        this.logger = logger;
    }

    public void saveCustomer(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            logger.error("Missing customer information");
            throw new IllegalArgumentException();
        }

        Customer customer = new Customer(firstName, lastName);
        try {
            entityManager.persist(customer);
            entityManager.flush();
            logger.info("Saved customer with id {}", customer.getId());

        } catch (Exception e) {
            logger.error("Error when saving customer: {}", e.getMessage());
        }
    }

}