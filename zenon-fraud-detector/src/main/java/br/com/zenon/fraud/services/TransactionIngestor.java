package br.com.zenon.fraud.services;

import br.com.zenon.fraud.model.TransactionCustomer;
import br.com.zenon.fraud.model.Payment;
import br.com.zenon.fraud.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    private final String transactionsFile;
    private final List<Transaction> transactions= new ArrayList<>();

    public TransactionIngestor(String transactionsFile) {
        this.transactionsFile = transactionsFile;
    }

    /***
     * @return List of transactions
     */
    public List<Transaction> getTransactions() {

        return transactions;
    }

    /***
     * Loads transactions from a file
     * @param total Number of transactions to load
     * @throws IOException Throws an exception if the file cannot be read
     */
    public void loadTransactions(int total) throws IOException  {

        FileReader fileReader = new FileReader(transactionsFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String header = bufferedReader.readLine();
        System.out.println("header = " + header);
        String line = bufferedReader.readLine();

        int count = 0;
        while (line != null && count < total) {
            transactions.add(mapTransaction(line));
            line = bufferedReader.readLine();
            count++;
        }
        bufferedReader.close();

    }

    /***
     * Maps a String line to a transaction
     * @param line String line that represents a transaction
     * @return Returns a transaction object
     */
    private Transaction mapTransaction(String line) {
        String[] fields = line.split(",");
        TransactionCustomer origin = new TransactionCustomer(fields[3], new BigDecimal(fields[4]), new BigDecimal(fields[5]));
        TransactionCustomer recipient = new TransactionCustomer(fields[6], new BigDecimal(fields[7]), new BigDecimal(fields[8]));
        return new Transaction(
                Integer.parseInt(fields[0]),
                Payment.valueOf(fields[1]),
                new BigDecimal(fields[2]),
                origin,
                recipient,
                Integer.parseInt(fields[9]),
                Integer.parseInt(fields[10])
        );
    }

}
