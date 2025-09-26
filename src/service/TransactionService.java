package service;

import model.Transaction;
import model.Compte;
import model.enums.TypeTransaction;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Map;

public class TransactionService {
    private TransactionRepository transactionRepository;
    
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    public List<Transaction> getTransactionsByCompte(Compte compte) {
        return transactionRepository.findAll().stream()
            .filter(t -> t.getCompteSource().equals(compte) || 
                        (t.getCompteDestination() != null && t.getCompteDestination().equals(compte)))
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsByType(Compte compte, TypeTransaction type) {
        return getTransactionsByCompte(compte).stream()
            .filter(t -> t.getTypeTransaction() == type)
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsByDateRange(Compte compte, LocalDate startDate, LocalDate endDate) {
        return getTransactionsByCompte(compte).stream()
            .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsSortedByAmount(Compte compte, boolean ascending) {
        return getTransactionsByCompte(compte).stream()
            .sorted(ascending ? 
                Comparator.comparing(Transaction::getMontant) : 
                Comparator.comparing(Transaction::getMontant).reversed())
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsSortedByDate(Compte compte, boolean ascending) {
        return getTransactionsByCompte(compte).stream()
            .sorted(ascending ? 
                Comparator.comparing(Transaction::getDate) : 
                Comparator.comparing(Transaction::getDate).reversed())
            .collect(Collectors.toList());
    }
    
    public double getTotalDepots(Compte compte) {
        return getTransactionsByType(compte, TypeTransaction.DEPOT).stream()
            .mapToDouble(Transaction::getMontant)
            .sum();
    }
    
    public double getTotalRetraits(Compte compte) {
        return getTransactionsByType(compte, TypeTransaction.RETRAIT).stream()
            .mapToDouble(Transaction::getMontant)
            .sum();
    }
    
    public double getTotalVirements(Compte compte) {
        return getTransactionsByType(compte, TypeTransaction.VIREMENT).stream()
            .filter(t -> t.getCompteSource().equals(compte))
            .mapToDouble(Transaction::getMontant)
            .sum();
    }
    
    public List<Transaction> getTransactionsSuspectes(double seuilMontant) {
        return transactionRepository.findAll().stream()
            .filter(t -> t.getMontant() > seuilMontant)
            .collect(Collectors.toList());
    }
    
    public Map<TypeTransaction, Long> getTransactionCountByType(Compte compte) {
        return getTransactionsByCompte(compte).stream()
            .collect(Collectors.groupingBy(
                Transaction::getTypeTransaction,
                Collectors.counting()
            ));
    }
}
