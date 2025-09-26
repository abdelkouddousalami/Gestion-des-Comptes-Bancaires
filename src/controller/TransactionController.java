package controller;

import model.Transaction;
import model.Compte;
import model.enums.TypeTransaction;
import service.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TransactionController {
    private TransactionService transactionService;
    
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    public List<Transaction> getTransactionsByCompte(Compte compte) {
        return transactionService.getTransactionsByCompte(compte);
    }
    
    public List<Transaction> getTransactionsByType(Compte compte, TypeTransaction type) {
        return transactionService.getTransactionsByType(compte, type);
    }
    
    public List<Transaction> getTransactionsByDateRange(Compte compte, LocalDate startDate, LocalDate endDate) {
        return transactionService.getTransactionsByDateRange(compte, startDate, endDate);
    }
    
    public List<Transaction> getTransactionsSortedByAmount(Compte compte, boolean ascending) {
        return transactionService.getTransactionsSortedByAmount(compte, ascending);
    }
    
    public List<Transaction> getTransactionsSortedByDate(Compte compte, boolean ascending) {
        return transactionService.getTransactionsSortedByDate(compte, ascending);
    }
    
    public double getTotalDepots(Compte compte) {
        return transactionService.getTotalDepots(compte);
    }
    
    public double getTotalRetraits(Compte compte) {
        return transactionService.getTotalRetraits(compte);
    }
    
    public double getTotalVirements(Compte compte) {
        return transactionService.getTotalVirements(compte);
    }
    
    public List<Transaction> getTransactionsSuspectes(double seuilMontant) {
        return transactionService.getTransactionsSuspectes(seuilMontant);
    }
    
    public Map<TypeTransaction, Long> getTransactionCountByType(Compte compte) {
        return transactionService.getTransactionCountByType(compte);
    }
}
