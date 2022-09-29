package com.example.springspock.service;

import com.example.springspock.model.Customer;

public class UserRepository {

    public Customer saveCustomer(String firstName, String lastName) {
        return new Customer(firstName, lastName);
    }

}
