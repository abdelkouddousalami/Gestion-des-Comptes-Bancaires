package controller;

import model.Compte;
import service.CompteService;

public class CompteController {
    private CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    public void deposit(Compte compte, double montant) {
        compteService.deposit(compte, montant);
    }

    public void withdraw(Compte compte, double montant) {
        compteService.withdraw(compte, montant);
    }

    public void transfer(Compte source, Compte destination, double montant) {
        compteService.transfer(source, destination, montant);
    }
}
