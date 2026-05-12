package br.com.zenon.fraud.model;

import java.math.BigDecimal;

public record Transaction(
        int step,
        Payment type,
        BigDecimal amount,
        TransactionCustomer origin,
        TransactionCustomer recipient,
        int isFraud,
        int isFlaggedFraud) {
}

