package controller;

import model.Transaction;
import model.enums.TypeCompte;
import service.GestionnaireService;

import java.util.List;

public class GestionnaireController {
    private GestionnaireService gestionnaireService;
    
    public GestionnaireController(GestionnaireService gestionnaireService) {
        this.gestionnaireService = gestionnaireService;
    }
    
    public void creerClient(int idClient, String nom, String prenom, String email, String motDePasse) {
        gestionnaireService.creerClient(idClient, nom, prenom, email, motDePasse);
    }
    
    public void modifierClient(int idClient, String nom, String prenom, String email) {
        gestionnaireService.modifierClient(idClient, nom, prenom, email);
    }
    
    public void supprimerClient(int idClient) {
        gestionnaireService.supprimerClient(idClient);
    }
    
    public void creerCompte(int idClient, int idCompte, TypeCompte typeCompte) {
        gestionnaireService.creerCompte(idClient, idCompte, typeCompte);
    }
    
    public void supprimerCompte(int idCompte) {
        gestionnaireService.supprimerCompte(idCompte);
    }
    
    public void effectuerDepot(int idCompte, double montant, String motif) {
        gestionnaireService.effectuerDepot(idCompte, montant, motif);
    }
    
    public void effectuerRetrait(int idCompte, double montant, String motif) {
        gestionnaireService.effectuerRetrait(idCompte, montant, motif);
    }
    
    public void effectuerVirement(int idCompteSource, int idCompteDestination, double montant, String motif) {
        gestionnaireService.effectuerVirement(idCompteSource, idCompteDestination, montant, motif);
    }
    
    public List<Transaction> consulterTransactionsClient(int idClient) {
        return gestionnaireService.consulterTransactionsClient(idClient);
    }
    
    public List<Transaction> consulterTransactionsCompte(int idCompte) {
        return gestionnaireService.consulterTransactionsCompte(idCompte);
    }
    
    public List<Transaction> identifierTransactionsSuspectes(double seuil) {
        return gestionnaireService.identifierTransactionsSuspectes(seuil);
    }
}
