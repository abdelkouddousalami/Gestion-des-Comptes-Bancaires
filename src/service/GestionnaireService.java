package service;

import model.Client;
import model.Compte;
import model.Transaction;
import model.enums.TypeCompte;
import repository.CompteRepository;
import util.ValidationUtils;

import java.util.List;

public class GestionnaireService {
    private CompteRepository compteRepository;
    private ClientService clientService;
    private CompteService compteService;
    private TransactionService transactionService;
    
    public GestionnaireService(CompteRepository compteRepository,
                             ClientService clientService, CompteService compteService,
                             TransactionService transactionService) {
        this.compteRepository = compteRepository;
        this.clientService = clientService;
        this.compteService = compteService;
        this.transactionService = transactionService;
    }
    
    public void creerClient(int idClient, String nom, String prenom, String email, String motDePasse) {
        if (!ValidationUtils.isNotEmpty(nom) || !ValidationUtils.isNotEmpty(prenom)) {
            throw new IllegalArgumentException("Le nom et prenom ne peuvent pas etre vides");
        }
        if (!ValidationUtils.isValidEmail(email)) {
            throw new IllegalArgumentException("Email invalide");
        }
        if (!ValidationUtils.isValidPassword(motDePasse)) {
            throw new IllegalArgumentException("Mot de passe doit contenir au moins 6 caracteres");
        }
        
        Client client = new Client(idClient, nom, prenom, email, motDePasse);
        clientService.addClient(client);
    }
    
    public void modifierClient(int idClient, String nom, String prenom, String email) {
        Client client = clientService.getClientById(idClient);
        if (ValidationUtils.isNotEmpty(nom)) client.setNom(nom);
        if (ValidationUtils.isNotEmpty(prenom)) client.setPrenom(prenom);
        if (ValidationUtils.isValidEmail(email)) client.setEmail(email);
    }
    
    public void supprimerClient(int idClient) {
        clientService.removeClient(idClient);
    }
    
    public void creerCompte(int idClient, int idCompte, TypeCompte typeCompte) {
        Client client = clientService.getClientById(idClient);
        Compte compte = new Compte(idCompte, typeCompte);
        client.addCompte(compte);
        compteRepository.save(compte);
    }
    
    public void supprimerCompte(int idCompte) {
        Compte compte = compteRepository.getById(idCompte);
        if (compte.getClient() != null) {
            compte.getClient().removeCompte(compte);
        }
        compteRepository.delete(compte);
    }
    
    public void effectuerDepot(int idCompte, double montant, String motif) {
        Compte compte = compteRepository.getById(idCompte);
        compteService.deposit(compte, montant);
    }
    
    public void effectuerRetrait(int idCompte, double montant, String motif) {
        Compte compte = compteRepository.getById(idCompte);
        compteService.withdraw(compte, montant);
    }
    
    public void effectuerVirement(int idCompteSource, int idCompteDestination, double montant, String motif) {
        Compte compteSource = compteRepository.getById(idCompteSource);
        Compte compteDestination = compteRepository.getById(idCompteDestination);
        compteService.transfer(compteSource, compteDestination, montant);
    }
    
    public List<Transaction> consulterTransactionsClient(int idClient) {
        Client client = clientService.getClientById(idClient);
        return client.getComptes().stream()
            .flatMap(compte -> transactionService.getTransactionsByCompte(compte).stream())
            .collect(java.util.stream.Collectors.toList());
    }
    
    public List<Transaction> consulterTransactionsCompte(int idCompte) {
        Compte compte = compteRepository.getById(idCompte);
        return transactionService.getTransactionsByCompte(compte);
    }
    
    public List<Transaction> identifierTransactionsSuspectes(double seuil) {
        return transactionService.getTransactionsSuspectes(seuil);
    }
}
