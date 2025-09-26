package service;

import model.Compte;
import model.enums.TypeTransaction;
import model.Transaction;
import repository.CompteRepository;
import repository.TransactionRepository;
import model.exceptions.InsufficientBalanceException;
import model.exceptions.InvalidTransactionException;

public class CompteService {
    private CompteRepository compteRepository;
    private TransactionRepository transactionRepository;

    public CompteService(CompteRepository compteRepository, TransactionRepository transactionRepository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(Compte compte, double montant) {
        if (montant <= 0) throw new InvalidTransactionException("Montant doit etre positif!");
        compte.setSolde(compte.getSolde() + montant);
        Transaction t = new Transaction(transactionRepository.getNextId(), TypeTransaction.DEPOT, montant,
                "Depot", compte, null);
        compte.addTransaction(t);
        transactionRepository.save(t);
        compteRepository.update(compte);
    }

    public void withdraw(Compte compte, double montant) {
        if (montant <= 0) throw new InvalidTransactionException("Montant doit etre positif!");
        if (montant > compte.getSolde()) throw new InsufficientBalanceException("Solde insuffisant!");
        compte.setSolde(compte.getSolde() - montant);
        Transaction t = new Transaction(transactionRepository.getNextId(), TypeTransaction.RETRAIT, montant,
                "Retrait", compte, null);
        compte.addTransaction(t);
        transactionRepository.save(t);
        compteRepository.update(compte);
    }

    public void transfer(Compte source, Compte destination, double montant) {
        if (montant <= 0) throw new InvalidTransactionException("Montant doit etre positif!");
        if (montant > source.getSolde()) throw new InsufficientBalanceException("Solde insuffisant!");
        source.setSolde(source.getSolde() - montant);
        destination.setSolde(destination.getSolde() + montant);
        Transaction t = new Transaction(transactionRepository.getNextId(), TypeTransaction.VIREMENT, montant,
                "Virement", source, destination);
        source.addTransaction(t);
        destination.addTransaction(t);
        transactionRepository.save(t);
        compteRepository.update(source);
        compteRepository.update(destination);
    }
}
