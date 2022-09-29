package com.example.springspock

import com.example.springspock.model.Customer
import org.slf4j.Logger
import spock.lang.Specification

import javax.persistence.EntityManager

class CustomerDaoExceptionHandlingSpec extends Specification {

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

    void "customer not saved when invalid"() {
        given: "a customer dao that throws exception when trying to salve a entity"
        String firstName = null
        String lastName = null

        when: "that customer is saved in the DB"
        customerDao.saveCustomer(firstName, lastName)

        then: "the error is correctly logged"
        thrown IllegalArgumentException

        and: "persist is not called"
        0 * entityManager.persist(_ as Customer)

        and: "flush is not called"
        0 * entityManager.flush()
    }

    void "customer not saved when exception in entity manager"() {
        given: "a customer dao that throws exception when trying to salve a entity"
        entityManager.persist(_ as Customer) >> { throw new InternalError("Not possible to save customer") }

        when: "that customer is saved in the DB"
        customerDao.saveCustomer("Suzan", "Smith")

        then: "the error is correctly logged"
        1 * logger.error("Error when saving customer: {}", _)

        and: "flush is not called"
        0 * entityManager.flush()
    }

}
