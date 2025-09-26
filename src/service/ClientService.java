package service;

import model.Client;
import model.Transaction;
import model.enums.TypeTransaction;
import repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public void removeClient(int id) {
        Client client = clientRepository.getById(id);
        clientRepository.delete(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(int id) {
        return clientRepository.getById(id);
    }
    
    public List<Transaction> getTransactionsClient(int idClient) {
        Client client = getClientById(idClient);
        return client.getComptes().stream()
            .flatMap(compte -> compte.getTransactions().stream())
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsClientByType(int idClient, TypeTransaction type) {
        return getTransactionsClient(idClient).stream()
            .filter(t -> t.getTypeTransaction() == type)
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsClientSortedByAmount(int idClient, boolean ascending) {
        return getTransactionsClient(idClient).stream()
            .sorted(ascending ? 
                Comparator.comparing(Transaction::getMontant) : 
                Comparator.comparing(Transaction::getMontant).reversed())
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsClientSortedByDate(int idClient, boolean ascending) {
        return getTransactionsClient(idClient).stream()
            .sorted(ascending ? 
                Comparator.comparing(Transaction::getDate) : 
                Comparator.comparing(Transaction::getDate).reversed())
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsClientByDateRange(int idClient, LocalDate startDate, LocalDate endDate) {
        return getTransactionsClient(idClient).stream()
            .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
            .collect(Collectors.toList());
    }
    
    public double getTotalDepotsClient(int idClient) {
        return getTransactionsClientByType(idClient, TypeTransaction.DEPOT).stream()
            .mapToDouble(Transaction::getMontant)
            .sum();
    }
    
    public double getTotalRetraitsClient(int idClient) {
        return getTransactionsClientByType(idClient, TypeTransaction.RETRAIT).stream()
            .mapToDouble(Transaction::getMontant)
            .sum();
    }
    
    public double getSoldeTotalClient(int idClient) {
        Client client = getClientById(idClient);
        return client.getSoldeTotal();
    }
}
