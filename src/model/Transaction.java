package model;

import model.enums.TypeTransaction;
import java.time.LocalDate;

public class Transaction {
    private int idTransaction;
    private TypeTransaction typeTransaction;
    private double montant;
    private LocalDate date;
    private String motif;
    private Compte compteSource;
    private Compte compteDestination; // optional for transfer

    public Transaction(int idTransaction, TypeTransaction typeTransaction, double montant, String motif,
                       Compte compteSource, Compte compteDestination) {
        this.idTransaction = idTransaction;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.date = LocalDate.now();
        this.motif = motif;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }

    public int getIdTransaction() { return idTransaction; }
    public void setIdTransaction(int idTransaction) { this.idTransaction = idTransaction; }
    
    public TypeTransaction getTypeTransaction() { return typeTransaction; }
    public void setTypeTransaction(TypeTransaction typeTransaction) { this.typeTransaction = typeTransaction; }
    
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    
    public Compte getCompteSource() { return compteSource; }
    public void setCompteSource(Compte compteSource) { this.compteSource = compteSource; }
    
    public Compte getCompteDestination() { return compteDestination; }
    public void setCompteDestination(Compte compteDestination) { this.compteDestination = compteDestination; }
}
