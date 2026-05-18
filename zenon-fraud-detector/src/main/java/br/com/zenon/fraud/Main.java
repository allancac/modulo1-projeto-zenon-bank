package br.com.zenon.fraud;

import br.com.zenon.fraud.service.TransactionIngestor;

import java.io.IOException;


public class Main {
    static void main(String[] args) throws IOException {

        String transactionsFile = "data/paysim_with_bad_data.csv";
        TransactionIngestor transactionIngestor = new TransactionIngestor(transactionsFile);
        transactionIngestor.loadTransactions();
        var transactions = transactionIngestor.getTransactions();
        transactions.forEach(System.out::println);
    }
}
