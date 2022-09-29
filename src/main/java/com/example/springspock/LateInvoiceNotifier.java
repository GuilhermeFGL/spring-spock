package com.example.springspock;

import com.example.springspock.model.Customer;
import com.example.springspock.model.Event;
import com.example.springspock.service.EmailSender;
import com.example.springspock.service.InvoiceStorage;

import java.time.LocalDate;

public class LateInvoiceNotifier {

    private final EmailSender emailSender;
    private final InvoiceStorage invoiceStorage;
    private final EventRecorder eventRecorder;
    private final CustomerReader customerReader;

    public LateInvoiceNotifier(final EmailSender emailSender,
                               final InvoiceStorage invoiceStorage,
                               final EventRecorder eventRecorder,
                               final CustomerReader customerReader) {
        this.emailSender = emailSender;
        this.invoiceStorage = invoiceStorage;
        this.eventRecorder = eventRecorder;
        this.customerReader = customerReader;
    }

    public void notifyIfLate(Customer customer) {
        if (invoiceStorage.hasOutstandingInvoice(customer)) {
            emailSender.sendEmail(customer);

            Event event = new Event();
            event.setTimestamp(LocalDate.now());
            event.setType(Event.Type.REMINDER_SENT);
            event.setCustomerName(this.customerReader.findFullName(customer.getId()));
            eventRecorder.recordEvent(event);
        }
    }

}