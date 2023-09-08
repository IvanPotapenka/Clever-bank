package by.potapenko.service;

import by.potapenko.database.dao.BankDao;
import by.potapenko.database.entity.BankEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class BankService {
    private static final BankService INSTANCE = new BankService();
    private final BankDao bankDao = BankDao.getInstance();

    public Optional<BankEntity> create(BankEntity bank) {
        return bankDao.create(bank);
    }

    public Optional<BankEntity> findByBank(String bankName) {
        return bankDao.findByBank(bankName);
    }

    public Optional<BankEntity> update(BankEntity bank) {
        return bankDao.update(bank);
    }

    public List<BankEntity> findAll() {
        return bankDao.findAll();
    }

    public void deleteById(Long id) {
        bankDao.deleteById(id);
    }

    public static BankService getInstance() {
        return INSTANCE;
    }
}
