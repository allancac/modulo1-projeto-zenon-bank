package br.com.zenon.fraud;

import br.com.zenon.fraud.model.Transaction;
import br.com.zenon.fraud.services.TransactionIngestor;

import java.io.IOException;
import java.util.List;


public class Main {
    static void main(String[] args) throws IOException {

        String transactionsFile = "data/PS_20174392719_1491204439457_log.csv";
        TransactionIngestor transactionIngestor = new TransactionIngestor(transactionsFile);
        transactionIngestor.loadTransactions(1000);
        List<Transaction> transactionsTest = transactionIngestor.getTransactions();
        transactionsTest.forEach(System.out::println);

    }
}
