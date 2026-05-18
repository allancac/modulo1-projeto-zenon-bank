package br.com.zenon.fraud.model;

import java.math.BigDecimal;

public record TransactionCustomer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance){

    public TransactionCustomer(String name, BigDecimal oldBalance, BigDecimal newBalance) {
        if(name != null || !name.isBlank())
            this.name = name;
        else
            throw new IllegalArgumentException("name should not be null or blank: " + name);
        if(oldBalance !=null && oldBalance.compareTo(BigDecimal.ZERO) >= 0)
            this.oldBalance = oldBalance;
        else
            throw new IllegalArgumentException("oldBalance should be positive: " + oldBalance);
        if(newBalance !=null && newBalance.compareTo(BigDecimal.ZERO) >= 0)
            this.newBalance = newBalance;
        else
            throw new IllegalArgumentException("newBalance should be positive: " + newBalance);
    }
}
