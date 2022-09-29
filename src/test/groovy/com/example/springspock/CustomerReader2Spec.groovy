package com.example.springspock

import com.example.springspock.model.Customer
import spock.lang.Specification

import javax.persistence.EntityManager

class CustomerReader2Spec extends Specification {

    //Class to be tested
    private CustomerReader customerReader

    //Dependencies
    private EntityManager entityManager

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    void setup() {
        customerReader = new CustomerReader()

        entityManager = Stub(EntityManager.class)
        customerReader.setEntityManager(entityManager)
    }


    void "customer full name is formed from first name and last name"() {
        given: "a customer with example name values"
        Customer sampleCustomer = new Customer()
        sampleCustomer.setFirstName("Susan")
        sampleCustomer.setLastName("Smith")

        and: "an entity manager that always returns this customer"
        entityManager.find(Customer.class, 1L) >> sampleCustomer

        when: "we ask for the full name of the customer"
        String fullName = customerReader.findFullName(1L)

        then: "we get both first and last name"
        fullName == "Susan Smith"
    }

    void "customer is not in the database"() {
        given: "the database has no record for the customer"
        entityManager.find(Customer.class, 1L) >> null

        when: "we ask for the full name of the customer"
        String fullName = customerReader.findFullName(1L)

        then: "the empty string should be returned"
        fullName == ""
    }

}
