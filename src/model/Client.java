package model;

import java.util.ArrayList;
import java.util.List;

public class Client extends Personne {
    private int idClient;
    private List<Compte> comptes;

    public Client(int idClient, String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.idClient = idClient;
        this.comptes = new ArrayList<>();
    }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    
    public List<Compte> getComptes() { return comptes; }
    public void setComptes(List<Compte> comptes) { this.comptes = comptes; }

    public void addCompte(Compte compte) {
        comptes.add(compte);
        compte.setClient(this);
    }
    
    public void removeCompte(Compte compte) {
        comptes.remove(compte);
    }
    
    public double getSoldeTotal() {
        return comptes.stream().mapToDouble(Compte::getSolde).sum();
    }
}
