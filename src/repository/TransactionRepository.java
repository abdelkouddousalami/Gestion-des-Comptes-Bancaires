package repository;

import model.Transaction;
import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findAll();
    int getNextId();
    void delete(Transaction transaction);
}
