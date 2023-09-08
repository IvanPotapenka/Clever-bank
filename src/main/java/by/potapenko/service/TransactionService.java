package by.potapenko.service;

import by.potapenko.database.dao.TransactionDao;
import by.potapenko.database.entity.TransactionEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TransactionService {
    private static final TransactionService INSTANCE = new TransactionService();
    private final TransactionDao transactionDao = TransactionDao.getInstance();

    public Optional<TransactionEntity> create(TransactionEntity transaction) {
        Optional<TransactionEntity> transactionEntity = transactionDao.create(transaction);
        OutPutService.writeChequePdfFile(transactionEntity.get());
        return transactionEntity;
    }

    public Optional<TransactionEntity> update(TransactionEntity transaction) {
        return transactionDao.update(transaction);
    }

    public List<TransactionEntity> findAll() {
        return transactionDao.findAll();
    }

    public void deleteById(Long id) {
        transactionDao.deleteById(id);
    }

    public List<TransactionEntity> findAllByAccountAndDate(Long id, String dateFrom, String dateTo) {
        return transactionDao.findAllByAccountAndDate(id, dateFrom, dateTo);
    }

    public static TransactionService getInstance() {
        return INSTANCE;
    }
}
