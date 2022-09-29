package com.example.springspock

import com.example.springspock.model.Customer
import org.slf4j.Logger
import spock.lang.Specification

import javax.persistence.EntityManager

class CustomerDaoSpec extends Specification {

    // Class to be tested
    private CustomerDao customerDao

    // Dependencies (will be mocked)
    private EntityManager entityManager
    private Logger logger

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    void setup() {
        entityManager = Stub(EntityManager.class)
        logger = Mock(Logger.class)

        customerDao = new CustomerDao(entityManager, logger)
    }

    void "customer IDs are logged whenever they are saved in the DB"() {
        given: "a customer dao that assigns an ID to customer"
        entityManager.persist(_ as Customer) >> { Customer customer -> customer.setId(123L) }

        when: "that customer is saved in the DB"
        customerDao.saveCustomer("Suzan", "Smith")

        then: "the ID is correctly logged"
        1 * logger.info("Saved customer with id {}", 123L)

    }
}
