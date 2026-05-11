package br.com.zenon.fraud.model;

import java.math.BigDecimal;

public record Transaction(
        int step,
        Payment type,
        BigDecimal amount,
        OriginClient originClient,
        DestinyClient destinyClient,
        int isFraud,
        int isFlaggedFraud) {
}

