package com.example.springspock.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Event {

    public enum Type {
        REMINDER_SENT, REGISTRATION, INVOICE_ISSUED, PAYMENT, SETTLEMENT
    }

    private Type type;
    private String customerName;
    private LocalDate timestamp;

}