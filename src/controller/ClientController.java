package controller;

import model.Client;
import model.Transaction;
import model.enums.TypeTransaction;
import service.ClientService;

import java.time.LocalDate;
import java.util.List;

public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void addClient(Client client) {
        clientService.addClient(client);
    }

    public void removeClient(int id) {
        clientService.removeClient(id);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public Client getClientById(int id) {
        return clientService.getClientById(id);
    }
    
    public List<Transaction> getTransactionsClient(int idClient) {
        return clientService.getTransactionsClient(idClient);
    }
    
    public List<Transaction> getTransactionsClientByType(int idClient, TypeTransaction type) {
        return clientService.getTransactionsClientByType(idClient, type);
    }
    
    public List<Transaction> getTransactionsClientSortedByAmount(int idClient, boolean ascending) {
        return clientService.getTransactionsClientSortedByAmount(idClient, ascending);
    }
    
    public List<Transaction> getTransactionsClientSortedByDate(int idClient, boolean ascending) {
        return clientService.getTransactionsClientSortedByDate(idClient, ascending);
    }
    
    public List<Transaction> getTransactionsClientByDateRange(int idClient, LocalDate startDate, LocalDate endDate) {
        return clientService.getTransactionsClientByDateRange(idClient, startDate, endDate);
    }
    
    public double getTotalDepotsClient(int idClient) {
        return clientService.getTotalDepotsClient(idClient);
    }
    
    public double getTotalRetraitsClient(int idClient) {
        return clientService.getTotalRetraitsClient(idClient);
    }
    
    public double getSoldeTotalClient(int idClient) {
        return clientService.getSoldeTotalClient(idClient);
    }
}
