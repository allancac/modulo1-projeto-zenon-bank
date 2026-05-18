package br.com.zenon.fraud.service;

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
    private final List<Transaction> transactions = new ArrayList<>();

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

        try (FileReader fileReader = new FileReader(transactionsFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String header = bufferedReader.readLine();
            String line = bufferedReader.readLine();
            int totalErrors = 0;

            int count = 0;
            while (line != null && count++ < total) {
                Transaction transaction = null;
                try {
                    transaction = mapTransaction(line);
                    transactions.add(transaction);
                }catch (IllegalArgumentException e){
                    System.err.printf("Erro: %s | %s: %s\n", line, e.getClass().getCanonicalName(), e.getMessage());
                    totalErrors++;
                }finally {
                    line = bufferedReader.readLine();
                }
            }
            System.out.println("totalErrors = " + totalErrors);
            bufferedReader.close();
        }

    }

    public void loadTransactions() throws IOException {


        try (FileReader fileReader = new FileReader(transactionsFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String header = bufferedReader.readLine();
            String line = bufferedReader.readLine();
            int totalErrors = 0;

            while (line != null) {
                Transaction transaction = null;
                try {
                    transaction = mapTransaction(line);
                    transactions.add(transaction);
                }catch (IllegalArgumentException e){
                    System.err.printf("Erro: %s | %s: %s\n", line, e.getClass().getCanonicalName(), e.getMessage());
                    totalErrors++;
                }finally {
                    line = bufferedReader.readLine();
                }
            }
            System.out.println("totalErrors = " + totalErrors);
            bufferedReader.close();
        }

    }


    /***
     * Maps a String line to a transaction
     * @param line String line that represents a transaction
     * @return Returns a transaction object
     */
    private Transaction mapTransaction(String line) throws IllegalArgumentException{
        String[] fields = line.split(",");
        TransactionCustomer origin = null;
        TransactionCustomer recipient = null;
        Transaction transaction = null;
        try {
            origin = new TransactionCustomer(fields[3], new BigDecimal(fields[4]), new BigDecimal(fields[5]));
            recipient = new TransactionCustomer(fields[6], new BigDecimal(fields[7]), new BigDecimal(fields[8]));
            transaction = new Transaction(
                    Integer.parseInt(fields[0]),
                    Payment.valueOf(fields[1]),
                    new BigDecimal(fields[2]),
                    origin,
                    recipient,
                    Integer.parseInt(fields[9]),
                    Integer.parseInt(fields[10])
            );
        } catch (IllegalArgumentException e) {
           throw new IllegalArgumentException(e.getMessage());
        }
        return transaction;

    }

}
