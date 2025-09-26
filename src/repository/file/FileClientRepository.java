package repository.file;

import model.Client;
import model.Compte;
import model.enums.TypeCompte;
import model.exceptions.ClientNotFoundException;
import repository.ClientRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileClientRepository implements ClientRepository {
    private static final String FILE_PATH = "data/clients.txt";
    private List<Client> clients;
    
    public FileClientRepository() {
        this.clients = new ArrayList<>();
        createDataDirectory();
        loadFromFile();
    }
    
    private void createDataDirectory() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public void save(Client client) {
        Optional<Client> existing = findById(client.getIdClient());
        if (existing.isPresent()) {
            update(client);
        } else {
            clients.add(client);
        }
        saveToFile();
    }
    
    public void delete(Client client) {
        clients.remove(client);
        saveToFile();
    }
    
    public Optional<Client> findById(int id) {
        return clients.stream().filter(c -> c.getIdClient() == id).findFirst();
    }
    
    public List<Client> findAll() {
        return new ArrayList<>(clients);
    }
    
    public Client getById(int id) {
        return findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
    }
    
    public void update(Client client) {
        Optional<Client> existingClient = findById(client.getIdClient());
        if (existingClient.isPresent()) {
            int index = clients.indexOf(existingClient.get());
            clients.set(index, client);
            saveToFile();
        } else {
            throw new ClientNotFoundException("Client not found for update with ID: " + client.getIdClient());
        }
    }
    
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Client client : clients) {
                writer.println(clientToString(client));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des clients: " + e.getMessage());
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
                    Client client = stringToClient(line);
                    if (client != null) {
                        clients.add(client);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des clients: " + e.getMessage());
        }
    }
    
    private String clientToString(Client client) {
        StringBuilder sb = new StringBuilder();
        sb.append(client.getIdClient()).append("|")
          .append(client.getNom()).append("|")
          .append(client.getPrenom()).append("|")
          .append(client.getEmail()).append("|")
          .append(client.getMotDePasse()).append("|")
          .append(client.getComptes().size());
        
        for (Compte compte : client.getComptes()) {
            sb.append("|").append(compte.getIdCompte())
              .append(":").append(compte.getTypeCompte())
              .append(":").append(compte.getSolde());
        }
        
        return sb.toString();
    }
    
    private Client stringToClient(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 6) return null;
            
            int idClient = Integer.parseInt(parts[0]);
            String nom = parts[1];
            String prenom = parts[2];
            String email = parts[3];
            String motDePasse = parts[4];
            int nbComptes = Integer.parseInt(parts[5]);
            
            Client client = new Client(idClient, nom, prenom, email, motDePasse);
            
            for (int i = 0; i < nbComptes && (6 + i) < parts.length; i++) {
                String[] compteInfo = parts[6 + i].split(":");
                if (compteInfo.length >= 3) {
                    int idCompte = Integer.parseInt(compteInfo[0]);
                    TypeCompte typeCompte = TypeCompte.valueOf(compteInfo[1]);
                    double solde = Double.parseDouble(compteInfo[2]);
                    
                    Compte compte = new Compte(idCompte, typeCompte);
                    compte.setSolde(solde);
                    client.addCompte(compte);
                }
            }
            
            return client;
        } catch (Exception e) {
            System.err.println("Erreur lors de la conversion de la ligne: " + line + " - " + e.getMessage());
            return null;
        }
    }
}
