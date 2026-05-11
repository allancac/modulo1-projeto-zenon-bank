package br.com.zenon.fraud.model;

import java.math.BigDecimal;

public record OriginClient(
        String nameOrig,
        BigDecimal oldbalanceOrg,
        BigDecimal newbalanceOrig){

        }
