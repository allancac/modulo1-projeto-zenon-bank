package br.com.zenon.fraud.service;

import br.com.zenon.fraud.model.TransactionCustomer;
import br.com.zenon.fraud.model.Payment;
import br.com.zenon.fraud.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            int totalErrors = 0;

            int count = 0;
            while (line != null && count++ < total) {
                Transaction transaction ;
                try {
                    final int numeroLinha = count;
                    transaction = mapTransaction(line).orElseThrow(
                            ()-> new IllegalArgumentException("Linha " + numeroLinha + " em branco")
                    );
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
        loadTransactions(Integer.MAX_VALUE);
    }

    /***
     * Maps a String line to a transaction
     * @param line String line that represents a transaction
     * @return Returns a transaction object
     */
    private Optional<Transaction> mapTransaction(String line) throws IllegalArgumentException {

        List<String> fields = Arrays.stream(line.split(",")).toList();
        boolean isEmptyLine = fields.stream().allMatch(String::isBlank);
        if ( isEmptyLine) {
            return Optional.empty();
        }
        TransactionCustomer origin ;
        TransactionCustomer recipient ;
        Transaction transaction ;
        try {
            origin = new TransactionCustomer(fields.get(3), new BigDecimal(fields.get(4)), new BigDecimal(fields.get(5)));
            recipient = new TransactionCustomer(fields.get(6), new BigDecimal(fields.get(7)), new BigDecimal(fields.get(8)));
            transaction = new Transaction(
                    Integer.parseInt(fields.get(0)),
                    Payment.valueOf(fields.get(1)),
                    new BigDecimal(fields.get(2)),
                    origin,
                    recipient,
                    Integer.parseInt(fields.get(9)),
                    Integer.parseInt(fields.get(10))
            );
        } catch (IllegalArgumentException e) {
           throw new IllegalArgumentException(e.getMessage());
        }
        return Optional.of(transaction);

    }

}
