package view;

import controller.ClientController;
import controller.CompteController;
import controller.GestionnaireController;
import util.ConsoleUtils;

public class MainMenu {
    private ClientController clientController;
    private ClientView clientView;
    private GestionnaireView gestionnaireView;

    public MainMenu(ClientController clientController, CompteController compteController, 
                   GestionnaireController gestionnaireController) {
        this.clientController = clientController;
        this.clientView = new ClientView(clientController);
        this.gestionnaireView = new GestionnaireView(gestionnaireController);
    }

    public void start() {
        System.out.println("Bienvenue dans l'application bancaire!");
        
        while (true) {
            System.out.println("\n===== Menu Principal =====");
            System.out.println("1. Connexion Client");
            System.out.println("2. Connexion Gestionnaire");
            System.out.println("3. Quitter");
            int choice = ConsoleUtils.readInt("Votre choix");

            switch (choice) {
                case 1:
                    connexionClient();
                    break;
                case 2:
                    connexionGestionnaire();
                    break;
                case 3:
                    System.out.println("Au revoir!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
    
    private void connexionClient() {
        try {
            int idClient = ConsoleUtils.readInt("ID Client");
            String motDePasse = ConsoleUtils.readString("Mot de passe");
            
            // Verification simple de l'existence du client
            var client = clientController.getClientById(idClient);
            if (client.getMotDePasse().equals(motDePasse)) {
                System.out.println("Connexion reussie! Bienvenue " + client.getPrenom() + " " + client.getNom());
                clientView.showClientMenu(idClient);
            } else {
                System.out.println("Mot de passe incorrect!");
            }
        } catch (Exception e) {
            System.out.println("Erreur de connexion: " + e.getMessage());
        }
    }
    
    private void connexionGestionnaire() {
        // Connexion simplifiee pour le gestionnaire
        String motDePasse = ConsoleUtils.readString("Mot de passe gestionnaire");
        if ("admin123".equals(motDePasse)) {
            System.out.println("Connexion gestionnaire reussie!");
            gestionnaireView.showGestionnaireMenu();
        } else {
            System.out.println("Mot de passe gestionnaire incorrect!");
        }
    }
}
