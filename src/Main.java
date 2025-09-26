import controller.ClientController;
import controller.CompteController;
import controller.GestionnaireController;
import repository.ClientRepository;
import repository.CompteRepository;
import repository.TransactionRepository;
import repository.file.FileClientRepository;
import repository.file.FileCompteRepository;
import repository.file.FileTransactionRepository;
import service.ClientService;
import service.CompteService;
import service.GestionnaireService;
import service.TransactionService;
import view.MainMenu;

public class Main {
    public static void main(String[] args) {
        // Repositories
        ClientRepository clientRepo = new FileClientRepository();
        CompteRepository compteRepo = new FileCompteRepository();
        TransactionRepository transactionRepo = new FileTransactionRepository();

        // Services
        ClientService clientService = new ClientService(clientRepo);
        CompteService compteService = new CompteService(compteRepo, transactionRepo);
        TransactionService transactionService = new TransactionService(transactionRepo);
        GestionnaireService gestionnaireService = new GestionnaireService(compteRepo, clientService, 
                                                                         compteService, transactionService);

        // Controllers
        ClientController clientController = new ClientController(clientService);
        CompteController compteController = new CompteController(compteService);
        GestionnaireController gestionnaireController = new GestionnaireController(gestionnaireService);

        // Initialisation des donnees de test seulement si aucun client n'existe
        if (clientController.getAllClients().isEmpty()) {
            initializeTestData(clientController, compteController, compteRepo);
        } else {
            System.out.println("Donnees existantes chargees depuis les fichiers.");
            System.out.println("- Nombre de clients: " + clientController.getAllClients().size());
            System.out.println("- Gestionnaire: Mot de passe=admin123");
            
            // Synchroniser les associations entre comptes et clients
            synchronizeData(clientRepo, compteRepo, transactionRepo);
        }

        // Main Menu
        MainMenu menu = new MainMenu(clientController, compteController, gestionnaireController);
        menu.start();
    }
    
    private static void initializeTestData(ClientController clientController, 
                                         CompteController compteController, 
                                         CompteRepository compteRepo) {
        try {
            // Creation de clients de test
            model.Client client1 = new model.Client(1, "Alami", "Mohamed", "mohamed.alami@email.com", "123456");
            model.Client client2 = new model.Client(2, "Benali", "Fatima", "fatima.benali@email.com", "654321");
            
            // Creation de comptes
            model.Compte compte1 = new model.Compte(101, model.enums.TypeCompte.COURANT);
            model.Compte compte2 = new model.Compte(102, model.enums.TypeCompte.EPARGNE);
            model.Compte compte3 = new model.Compte(103, model.enums.TypeCompte.COURANT);
            
            // Association clients-comptes
            client1.addCompte(compte1);
            client1.addCompte(compte2);
            client2.addCompte(compte3);
            
            // Sauvegarde
            clientController.addClient(client1);
            clientController.addClient(client2);
            compteRepo.save(compte1);
            compteRepo.save(compte2);
            compteRepo.save(compte3);
            
            // Quelques transactions de test
            compteController.deposit(compte1, 5000.0);
            compteController.deposit(compte2, 10000.0);
            compteController.deposit(compte3, 3000.0);
            
            compteController.withdraw(compte1, 500.0);
            compteController.transfer(compte1, compte3, 1000.0);
            
            System.out.println("Donnees de test initialisees:");
            System.out.println("- Client 1: ID=1, Mot de passe=123456");
            System.out.println("- Client 2: ID=2, Mot de passe=654321");
            System.out.println("- Gestionnaire: Mot de passe=admin123");
            
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation des donnees de test: " + e.getMessage());
        }
    }
    
    private static void synchronizeData(ClientRepository clientRepo, CompteRepository compteRepo, 
                                      TransactionRepository transactionRepo) {
        try {
            // Associer les comptes aux bons clients
            for (model.Client client : clientRepo.findAll()) {
                client.getComptes().clear();
                for (model.Compte compte : compteRepo.findAll()) {
                    // Cette logique devrait être améliorée avec une vraie association dans les fichiers
                    if ((client.getIdClient() == 1 && (compte.getIdCompte() == 101 || compte.getIdCompte() == 102)) ||
                        (client.getIdClient() == 2 && compte.getIdCompte() == 103)) {
                        client.addCompte(compte);
                    }
                }
            }
            
            // Recalculer les soldes des comptes à partir des transactions
            for (model.Compte compte : compteRepo.findAll()) {
                double solde = 0.0;
                for (model.Transaction transaction : transactionRepo.findAll()) {
                    if (transaction.getCompteSource().getIdCompte() == compte.getIdCompte()) {
                        if (transaction.getTypeTransaction() == model.enums.TypeTransaction.DEPOT) {
                            solde += transaction.getMontant();
                        } else if (transaction.getTypeTransaction() == model.enums.TypeTransaction.RETRAIT ||
                                  transaction.getTypeTransaction() == model.enums.TypeTransaction.VIREMENT) {
                            solde -= transaction.getMontant();
                        }
                    }
                    if (transaction.getCompteDestination() != null && 
                        transaction.getCompteDestination().getIdCompte() == compte.getIdCompte() &&
                        transaction.getTypeTransaction() == model.enums.TypeTransaction.VIREMENT) {
                        solde += transaction.getMontant();
                    }
                }
                compte.setSolde(solde);
            }
            
            System.out.println("Synchronisation des donnees terminee.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la synchronisation: " + e.getMessage());
        }
    }
}
