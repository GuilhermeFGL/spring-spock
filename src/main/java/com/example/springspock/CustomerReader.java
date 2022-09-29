package com.example.springspock;

import com.example.springspock.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomerReader {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String findFullName(Long customerID) {
        Customer customer = entityManager.find(Customer.class, customerID);
        if (customer == null) {
            return "";
        }
        return customer.getFirstName() + " " + customer.getLastName();
    }

}