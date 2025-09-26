package view;

import controller.GestionnaireController;
import model.Transaction;
import model.enums.TypeCompte;
import util.ConsoleUtils;

import java.util.List;

public class GestionnaireView {
    private GestionnaireController gestionnaireController;
    
    public GestionnaireView(GestionnaireController gestionnaireController) {
        this.gestionnaireController = gestionnaireController;
    }
    
    public void showGestionnaireMenu() {
        while (true) {
            System.out.println("\n===== Menu Gestionnaire =====");
            System.out.println("1. Gestion des clients");
            System.out.println("2. Gestion des comptes");
            System.out.println("3. Gestion des transactions");
            System.out.println("4. Consultation et rapports");
            System.out.println("5. Identification transactions suspectes");
            System.out.println("6. Retour");
            
            int choice = ConsoleUtils.readInt("Votre choix");
            
            switch (choice) {
                case 1:
                    menuGestionClients();
                    break;
                case 2:
                    menuGestionComptes();
                    break;
                case 3:
                    menuGestionTransactions();
                    break;
                case 4:
                    menuConsultationRapports();
                    break;
                case 5:
                    identifierTransactionsSuspectes();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void menuGestionClients() {
        while (true) {
            System.out.println("\n===== Gestion des Clients =====");
            System.out.println("1. Creer un client");
            System.out.println("2. Modifier un client");
            System.out.println("3. Supprimer un client");
            System.out.println("4. Retour");
            
            int choice = ConsoleUtils.readInt("Votre choix");
            
            switch (choice) {
                case 1:
                    creerClient();
                    break;
                case 2:
                    modifierClient();
                    break;
                case 3:
                    supprimerClient();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void menuGestionComptes() {
        while (true) {
            System.out.println("\n===== Gestion des Comptes =====");
            System.out.println("1. Creer un compte");
            System.out.println("2. Supprimer un compte");
            System.out.println("3. Retour");
            
            int choice = ConsoleUtils.readInt("Votre choix");
            
            switch (choice) {
                case 1:
                    creerCompte();
                    break;
                case 2:
                    supprimerCompte();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void menuGestionTransactions() {
        while (true) {
            System.out.println("\n===== Gestion des Transactions =====");
            System.out.println("1. Effectuer un depot");
            System.out.println("2. Effectuer un retrait");
            System.out.println("3. Effectuer un virement");
            System.out.println("4. Retour");
            
            int choice = ConsoleUtils.readInt("Votre choix");
            
            switch (choice) {
                case 1:
                    effectuerDepot();
                    break;
                case 2:
                    effectuerRetrait();
                    break;
                case 3:
                    effectuerVirement();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void menuConsultationRapports() {
        while (true) {
            System.out.println("\n===== Consultation et Rapports =====");
            System.out.println("1. Consulter transactions d'un client");
            System.out.println("2. Consulter transactions d'un compte");
            System.out.println("3. Retour");
            
            int choice = ConsoleUtils.readInt("Votre choix");
            
            switch (choice) {
                case 1:
                    consulterTransactionsClient();
                    break;
                case 2:
                    consulterTransactionsCompte();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void creerClient() {
        try {
            int idClient = ConsoleUtils.readInt("ID du client");
            String nom = ConsoleUtils.readString("Nom");
            String prenom = ConsoleUtils.readString("Prenom");
            String email = ConsoleUtils.readString("Email");
            String motDePasse = ConsoleUtils.readString("Mot de passe");
            
            gestionnaireController.creerClient(idClient, nom, prenom, email, motDePasse);
            System.out.println("Client cree avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la creation du client: " + e.getMessage());
        }
    }
    
    private void modifierClient() {
        try {
            int idClient = ConsoleUtils.readInt("ID du client a modifier");
            String nom = ConsoleUtils.readString("Nouveau nom (laisser vide pour ne pas modifier)");
            String prenom = ConsoleUtils.readString("Nouveau prenom (laisser vide pour ne pas modifier)");
            String email = ConsoleUtils.readString("Nouvel email (laisser vide pour ne pas modifier)");
            
            gestionnaireController.modifierClient(idClient, nom, prenom, email);
            System.out.println("Client modifie avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la modification du client: " + e.getMessage());
        }
    }
    
    private void supprimerClient() {
        try {
            int idClient = ConsoleUtils.readInt("ID du client a supprimer");
            gestionnaireController.supprimerClient(idClient);
            System.out.println("Client supprime avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du client: " + e.getMessage());
        }
    }
    
    private void creerCompte() {
        try {
            int idClient = ConsoleUtils.readInt("ID du client");
            int idCompte = ConsoleUtils.readInt("ID du nouveau compte");
            
            System.out.println("Types de compte disponibles:");
            System.out.println("1. COURANT");
            System.out.println("2. EPARGNE");
            System.out.println("3. DEPOTATERME");
            int typeChoix = ConsoleUtils.readInt("Choisir le type");
            
            TypeCompte typeCompte;
            switch (typeChoix) {
                case 1: typeCompte = TypeCompte.COURANT; break;
                case 2: typeCompte = TypeCompte.EPARGNE; break;
                case 3: typeCompte = TypeCompte.DEPOTATERME; break;
                default:
                    System.out.println("Type invalide!");
                    return;
            }
            
            gestionnaireController.creerCompte(idClient, idCompte, typeCompte);
            System.out.println("Compte cree avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la creation du compte: " + e.getMessage());
        }
    }
    
    private void supprimerCompte() {
        try {
            int idCompte = ConsoleUtils.readInt("ID du compte a supprimer");
            gestionnaireController.supprimerCompte(idCompte);
            System.out.println("Compte supprime avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du compte: " + e.getMessage());
        }
    }
    
    private void effectuerDepot() {
        try {
            int idCompte = ConsoleUtils.readInt("ID du compte");
            double montant = ConsoleUtils.readDouble("Montant du depot");
            String motif = ConsoleUtils.readString("Motif");
            
            gestionnaireController.effectuerDepot(idCompte, montant, motif);
            System.out.println("Depot effectue avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors du depot: " + e.getMessage());
        }
    }
    
    private void effectuerRetrait() {
        try {
            int idCompte = ConsoleUtils.readInt("ID du compte");
            double montant = ConsoleUtils.readDouble("Montant du retrait");
            String motif = ConsoleUtils.readString("Motif");
            
            gestionnaireController.effectuerRetrait(idCompte, montant, motif);
            System.out.println("Retrait effectue avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors du retrait: " + e.getMessage());
        }
    }
    
    private void effectuerVirement() {
        try {
            int idCompteSource = ConsoleUtils.readInt("ID du compte source");
            int idCompteDestination = ConsoleUtils.readInt("ID du compte destination");
            double montant = ConsoleUtils.readDouble("Montant du virement");
            String motif = ConsoleUtils.readString("Motif");
            
            gestionnaireController.effectuerVirement(idCompteSource, idCompteDestination, montant, motif);
            System.out.println("Virement effectue avec succes!");
        } catch (Exception e) {
            System.out.println("Erreur lors du virement: " + e.getMessage());
        }
    }
    
    private void consulterTransactionsClient() {
        try {
            int idClient = ConsoleUtils.readInt("ID du client");
            List<Transaction> transactions = gestionnaireController.consulterTransactionsClient(idClient);
            
            System.out.println("\n===== Transactions du Client " + idClient + " =====");
            afficherTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void consulterTransactionsCompte() {
        try {
            int idCompte = ConsoleUtils.readInt("ID du compte");
            List<Transaction> transactions = gestionnaireController.consulterTransactionsCompte(idCompte);
            
            System.out.println("\n===== Transactions du Compte " + idCompte + " =====");
            afficherTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void identifierTransactionsSuspectes() {
        try {
            double seuil = ConsoleUtils.readDouble("Seuil de montant suspect");
            List<Transaction> transactionsSuspectes = gestionnaireController.identifierTransactionsSuspectes(seuil);
            
            System.out.println("\n===== Transactions Suspectes (>" + seuil + " MAD) =====");
            afficherTransactions(transactionsSuspectes);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void afficherTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("Aucune transaction trouvee.");
            return;
        }
        
        transactions.forEach(transaction -> {
            System.out.println("ID: " + transaction.getIdTransaction());
            System.out.println("Type: " + transaction.getTypeTransaction());
            System.out.println("Montant: " + transaction.getMontant() + " MAD");
            System.out.println("Date: " + transaction.getDate());
            System.out.println("Motif: " + transaction.getMotif());
            System.out.println("Compte source: " + transaction.getCompteSource().getIdCompte());
            if (transaction.getCompteDestination() != null) {
                System.out.println("Compte destination: " + transaction.getCompteDestination().getIdCompte());
            }
            System.out.println("---");
        });
    }
}
