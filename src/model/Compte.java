package model;

import model.enums.TypeCompte;
import java.util.ArrayList;
import java.util.List;

public class Compte {
    private int idCompte;
    private TypeCompte typeCompte;
    private double solde;
    private List<Transaction> transactions;
    private Client client;

    public Compte(int idCompte, TypeCompte typeCompte) {
        this.idCompte = idCompte;
        this.typeCompte = typeCompte;
        this.solde = 0.0;
        this.transactions = new ArrayList<>();
    }

    public int getIdCompte() { return idCompte; }
    public void setIdCompte(int idCompte) { this.idCompte = idCompte; }
    
    public TypeCompte getTypeCompte() { return typeCompte; }
    public void setTypeCompte(TypeCompte typeCompte) { this.typeCompte = typeCompte; }
    
    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
    
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
    
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
