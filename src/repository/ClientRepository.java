package repository;

import model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    void save(Client client);
    void delete(Client client);
    Optional<Client> findById(int id);
    List<Client> findAll();
    Client getById(int id);
    void update(Client client);
}
