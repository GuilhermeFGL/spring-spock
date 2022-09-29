package com.example.springspock

import com.example.springspock.model.Customer
import com.example.springspock.model.Event
import com.example.springspock.service.EmailSender
import com.example.springspock.service.InvoiceStorage
import spock.lang.Specification

class EventCheckSpec extends Specification {

    //Class to be tested
    private LateInvoiceNotifier lateInvoiceNotifier

    //Dependencies (will be mocked)
    private EmailSender emailSender
    private InvoiceStorage invoiceStorage
    private EventRecorder eventRecorder
    private CustomerReader customerReader

    //Test data
    private Customer sampleCustomer

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    void setup() {
        invoiceStorage = Stub(InvoiceStorage.class)
        emailSender = Mock(EmailSender.class)
        eventRecorder = Mock(EventRecorder.class)
        customerReader = Stub(CustomerReader.class)

        lateInvoiceNotifier = new LateInvoiceNotifier(emailSender, invoiceStorage, eventRecorder, customerReader)
        sampleCustomer = new Customer()
        sampleCustomer.setId(1L)
        sampleCustomer.setFirstName("Susan")
        sampleCustomer.setLastName("Smith")
    }

    void "email about late invoice should contain customer details"() {
        given: "a customer with a late invoice"
        invoiceStorage.hasOutstandingInvoice(sampleCustomer) >> true

        and: "a customer with full name as Susan Smith"
        customerReader.findFullName(sampleCustomer.getId()) >> "Susan Smith"

        when: "we check if an email should be sent"
        lateInvoiceNotifier.notifyIfLate(sampleCustomer)

        then: "the customer is indeed emailed"
        1 * emailSender.sendEmail(sampleCustomer)

        and: "the event is recorded with the respective details"
        1 * eventRecorder.recordEvent({
            event ->
                event.getTimestamp() != null &&
                        event.getType() == Event.Type.REMINDER_SENT &&
                        event.getCustomerName() == "Susan Smith"
        })
    }

}
