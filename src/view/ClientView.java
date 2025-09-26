package view;

import controller.ClientController;
import model.Client;
import model.Transaction;
import model.enums.TypeTransaction;
import util.ConsoleUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ClientView {
    private ClientController clientController;
    
    public ClientView(ClientController clientController) {
        this.clientController = clientController;
    }
    
    public void showClientMenu(int idClient) {
        while (true) {
            System.out.println("\n===== Menu Client =====");
            System.out.println("1. Consulter mes informations");
            System.out.println("2. Consulter mes comptes");
            System.out.println("3. Historique des transactions");
            System.out.println("4. Filtrer transactions par type");
            System.out.println("5. Trier transactions par montant");
            System.out.println("6. Trier transactions par date");
            System.out.println("7. Filtrer par periode");
            System.out.println("8. Calculer totaux");
            System.out.println("9. Retour");
            
            int choice = ConsoleUtils.readInt("Votre choix");
            
            switch (choice) {
                case 1:
                    afficherInformationsClient(idClient);
                    break;
                case 2:
                    afficherComptes(idClient);
                    break;
                case 3:
                    afficherHistoriqueTransactions(idClient);
                    break;
                case 4:
                    filtrerTransactionsParType(idClient);
                    break;
                case 5:
                    trierTransactionsParMontant(idClient);
                    break;
                case 6:
                    trierTransactionsParDate(idClient);
                    break;
                case 7:
                    filtrerTransactionsParPeriode(idClient);
                    break;
                case 8:
                    calculerTotaux(idClient);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void afficherInformationsClient(int idClient) {
        try {
            Client client = clientController.getClientById(idClient);
            System.out.println("\n===== Informations Client =====");
            System.out.println("ID: " + client.getIdClient());
            System.out.println("Nom: " + client.getNom());
            System.out.println("Prenom: " + client.getPrenom());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Nombre de comptes: " + client.getComptes().size());
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void afficherComptes(int idClient) {
        try {
            Client client = clientController.getClientById(idClient);
            System.out.println("\n===== Mes Comptes =====");
            client.getComptes().forEach(compte -> {
                System.out.println("ID Compte: " + compte.getIdCompte());
                System.out.println("Type: " + compte.getTypeCompte());
                System.out.println("Solde: " + compte.getSolde() + " MAD");
                System.out.println("Nombre de transactions: " + compte.getTransactions().size());
                System.out.println("---");
            });
            System.out.println("Solde total: " + client.getSoldeTotal() + " MAD");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void afficherHistoriqueTransactions(int idClient) {
        try {
            List<Transaction> transactions = clientController.getTransactionsClient(idClient);
            System.out.println("\n===== Historique Transactions =====");
            afficherTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void filtrerTransactionsParType(int idClient) {
        try {
            System.out.println("Types disponibles:");
            System.out.println("1. DEPOT");
            System.out.println("2. RETRAIT");
            System.out.println("3. VIREMENT");
            int choix = ConsoleUtils.readInt("Choisir type");
            
            TypeTransaction type;
            switch (choix) {
                case 1: type = TypeTransaction.DEPOT; break;
                case 2: type = TypeTransaction.RETRAIT; break;
                case 3: type = TypeTransaction.VIREMENT; break;
                default:
                    System.out.println("Type invalide!");
                    return;
            }
            
            List<Transaction> transactions = clientController.getTransactionsClientByType(idClient, type);
            System.out.println("\n===== Transactions " + type + " =====");
            afficherTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void trierTransactionsParMontant(int idClient) {
        try {
            System.out.println("1. Croissant");
            System.out.println("2. Decroissant");
            int choix = ConsoleUtils.readInt("Ordre de tri");
            boolean ascending = choix == 1;
            
            List<Transaction> transactions = clientController.getTransactionsClientSortedByAmount(idClient, ascending);
            System.out.println("\n===== Transactions triees par montant =====");
            afficherTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void trierTransactionsParDate(int idClient) {
        try {
            System.out.println("1. Plus anciennes d'abord");
            System.out.println("2. Plus recentes d'abord");
            int choix = ConsoleUtils.readInt("Ordre de tri");
            boolean ascending = choix == 1;
            
            List<Transaction> transactions = clientController.getTransactionsClientSortedByDate(idClient, ascending);
            System.out.println("\n===== Transactions triees par date =====");
            afficherTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void filtrerTransactionsParPeriode(int idClient) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateDebutStr = ConsoleUtils.readString("Date debut (yyyy-MM-dd)");
            String dateFinStr = ConsoleUtils.readString("Date fin (yyyy-MM-dd)");
            
            LocalDate dateDebut = LocalDate.parse(dateDebutStr, formatter);
            LocalDate dateFin = LocalDate.parse(dateFinStr, formatter);
            
            List<Transaction> transactions = clientController.getTransactionsClientByDateRange(idClient, dateDebut, dateFin);
            System.out.println("\n===== Transactions de " + dateDebut + " a " + dateFin + " =====");
            afficherTransactions(transactions);
        } catch (DateTimeParseException e) {
            System.out.println("Format de date invalide! Utilisez yyyy-MM-dd");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
    
    private void calculerTotaux(int idClient) {
        try {
            double soldeTotalClient = clientController.getSoldeTotalClient(idClient);
            double totalDepots = clientController.getTotalDepotsClient(idClient);
            double totalRetraits = clientController.getTotalRetraitsClient(idClient);
            
            System.out.println("\n===== Totaux =====");
            System.out.println("Solde total: " + soldeTotalClient + " MAD");
            System.out.println("Total depots: " + totalDepots + " MAD");
            System.out.println("Total retraits: " + totalRetraits + " MAD");
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
