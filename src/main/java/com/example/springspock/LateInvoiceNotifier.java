package com.example.springspock;

import com.example.springspock.entity.Customer;
import com.example.springspock.service.EmailSender;
import com.example.springspock.service.InvoiceStorage;

public class LateInvoiceNotifier {

    private final EmailSender emailSender;
    private final InvoiceStorage invoiceStorage;

    public LateInvoiceNotifier(final EmailSender emailSender, final InvoiceStorage invoiceStorage) {
        this.emailSender = emailSender;
        this.invoiceStorage = invoiceStorage;
    }

    public void notifyIfLate(Customer customer) {
        if (invoiceStorage.hasOutstandingInvoice(customer)) {
            emailSender.sendEmail(customer);
        }
    }

}