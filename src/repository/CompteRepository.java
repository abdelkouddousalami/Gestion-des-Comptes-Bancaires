package repository;

import model.Compte;
import java.util.List;
import java.util.Optional;

public interface CompteRepository {
    void save(Compte compte);
    void delete(Compte compte);
    Optional<Compte> findById(int id);
    List<Compte> findAll();
    Compte getById(int id);
    void update(Compte compte);
}
