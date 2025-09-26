package repository.file;

import model.Transaction;
import model.Compte;
import model.enums.TypeTransaction;
import repository.TransactionRepository;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileTransactionRepository implements TransactionRepository {
    private static final String FILE_PATH = "data/transactions.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<Transaction> transactions;
    
    public FileTransactionRepository() {
        this.transactions = new ArrayList<>();
        createDataDirectory();
        loadFromFile();
    }
    
    private void createDataDirectory() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public void save(Transaction transaction) {
        transactions.add(transaction);
        saveToFile();
    }
    
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }
    
    public int getNextId() {
        return transactions.size() + 1;
    }
    
    public void delete(Transaction transaction) {
        transactions.remove(transaction);
        saveToFile();
    }
    
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Transaction transaction : transactions) {
                writer.println(transactionToString(transaction));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des transactions: " + e.getMessage());
        }
    }
    
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Transaction transaction = stringToTransaction(line);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des transactions: " + e.getMessage());
        }
    }
    
    private String transactionToString(Transaction transaction) {
        return transaction.getIdTransaction() + "|" +
               transaction.getTypeTransaction() + "|" +
               transaction.getMontant() + "|" +
               transaction.getDate().format(DATE_FORMATTER) + "|" +
               transaction.getMotif() + "|" +
               transaction.getCompteSource().getIdCompte() + "|" +
               (transaction.getCompteDestination() != null ? transaction.getCompteDestination().getIdCompte() : -1);
    }
    
    private Transaction stringToTransaction(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 7) {
                int idTransaction = Integer.parseInt(parts[0]);
                TypeTransaction typeTransaction = TypeTransaction.valueOf(parts[1]);
                double montant = Double.parseDouble(parts[2]);
                LocalDate date = LocalDate.parse(parts[3], DATE_FORMATTER);
                String motif = parts[4];
                int compteSourceId = Integer.parseInt(parts[5]);
                int compteDestinationId = parts[6].equals("-1") ? -1 : Integer.parseInt(parts[6]);
                
                // Note: Les comptes devront être réassociés après le chargement
                // Pour l'instant, on crée des comptes temporaires
                Compte compteSource = new Compte(compteSourceId, model.enums.TypeCompte.COURANT);
                Compte compteDestination = compteDestinationId != -1 ? 
                    new Compte(compteDestinationId, model.enums.TypeCompte.COURANT) : null;
                
                Transaction transaction = new Transaction(idTransaction, typeTransaction, montant, motif, 
                                                        compteSource, compteDestination);
                transaction.setDate(date);
                return transaction;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la conversion de la ligne: " + line + " - " + e.getMessage());
        }
        return null;
    }
}
