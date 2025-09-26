package repository.file;

import model.Compte;
import model.enums.TypeCompte;
import model.exceptions.CompteNotFoundException;
import repository.CompteRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCompteRepository implements CompteRepository {
    private static final String FILE_PATH = "data/comptes.txt";
    private List<Compte> comptes;
    
    public FileCompteRepository() {
        this.comptes = new ArrayList<>();
        createDataDirectory();
        loadFromFile();
    }
    
    private void createDataDirectory() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public void save(Compte compte) {
        Optional<Compte> existing = findById(compte.getIdCompte());
        if (existing.isPresent()) {
            update(compte);
        } else {
            comptes.add(compte);
        }
        saveToFile();
    }
    
    public void delete(Compte compte) {
        comptes.remove(compte);
        saveToFile();
    }
    
    public Optional<Compte> findById(int id) {
        return comptes.stream().filter(c -> c.getIdCompte() == id).findFirst();
    }
    
    public List<Compte> findAll() {
        return new ArrayList<>(comptes);
    }
    
    public Compte getById(int id) {
        return findById(id).orElseThrow(() -> new CompteNotFoundException("Compte not found with ID: " + id));
    }
    
    public void update(Compte compte) {
        Optional<Compte> existingCompte = findById(compte.getIdCompte());
        if (existingCompte.isPresent()) {
            int index = comptes.indexOf(existingCompte.get());
            comptes.set(index, compte);
            saveToFile();
        } else {
            throw new CompteNotFoundException("Compte not found for update with ID: " + compte.getIdCompte());
        }
    }
    
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Compte compte : comptes) {
                writer.println(compteToString(compte));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des comptes: " + e.getMessage());
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
                    Compte compte = stringToCompte(line);
                    if (compte != null) {
                        comptes.add(compte);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des comptes: " + e.getMessage());
        }
    }
    
    private String compteToString(Compte compte) {
        return compte.getIdCompte() + "|" + 
               compte.getTypeCompte() + "|" + 
               compte.getSolde() + "|" + 
               (compte.getClient() != null ? compte.getClient().getIdClient() : -1) + "|" +
               compte.getTransactions().size();
    }
    
    private Compte stringToCompte(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 5) {
                int idCompte = Integer.parseInt(parts[0]);
                TypeCompte typeCompte = TypeCompte.valueOf(parts[1]);
                double solde = Double.parseDouble(parts[2]);
                // int clientId = Integer.parseInt(parts[3]); // Sera associé plus tard
                // int nbTransactions = Integer.parseInt(parts[4]);
                
                Compte compte = new Compte(idCompte, typeCompte);
                compte.setSolde(solde);
                return compte;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la conversion de la ligne: " + line + " - " + e.getMessage());
        }
        return null;
    }
}
