package br.com.zenon.fraud.model;

import java.math.BigDecimal;

public record DestinyClient(
        String nameDest,
        BigDecimal oldbalanceDest,
        BigDecimal newbalanceDest
){

}
