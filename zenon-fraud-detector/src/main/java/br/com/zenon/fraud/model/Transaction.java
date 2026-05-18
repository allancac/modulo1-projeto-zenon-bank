package br.com.zenon.fraud.model;

import java.math.BigDecimal;

public record Transaction (
        int step,
        Payment type,
        BigDecimal amount,
        TransactionCustomer origin,
        TransactionCustomer recipient,
        boolean isFraud,
        boolean isFlaggedFraud) {

    public Transaction(int step, Payment type, BigDecimal amount, TransactionCustomer origin, TransactionCustomer recipient, int isFraud, int isFlaggedFraud) {
        this(
                validateStep(step),
                type,
                validateAmount(amount),
                origin,
                recipient,
                mapIsFraud(isFraud),
                mapIsFlaggedFraud(isFlaggedFraud)
        );
    }

    private static int validateStep(int step){
        if (step >=1) return step;
        else throw new IllegalArgumentException("step should be positive: " + step);
    }

    private static BigDecimal validateAmount(BigDecimal amount){
        if(amount !=null && amount.compareTo(BigDecimal.ZERO) > 0) return amount;
        else throw new IllegalArgumentException("amount should be positive: " + amount);
    }

    private static boolean mapIsFraud(int isFraud){
        if (isFraud == 0  ) return false;
        else if (isFraud ==1) return true;
        else throw new IllegalArgumentException("isFraud must be 0 or 1");
    }

    private static boolean mapIsFlaggedFraud(int isFlaggedFraud){
        if(isFlaggedFraud == 0) return false;
        else if(isFlaggedFraud == 1) return true;
        else throw new IllegalArgumentException("isFlaggedFraud must be 0 or 1");

    }

}

