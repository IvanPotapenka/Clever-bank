package by.potapenko.service;

import by.potapenko.database.dao.AccountDao;
import by.potapenko.database.entity.AccountEntity;
import by.potapenko.database.entity.BankEntity;
import by.potapenko.database.entity.TransactionEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class AccountService {
    private static final AccountService INSTANCE = new AccountService();
    private final AccountDao accountDao = AccountDao.getInstance();

    public Optional<AccountEntity> create(AccountEntity account, BankEntity bank) {
        Random random = new Random();
        account.setBalance(0.0);
        account.setInterestAmount(0.0);
        StringBuilder iban = new StringBuilder("BY");
        for (int i = 0; i < 2; i++) {
            iban.append(random.nextInt(10));
        }
        iban.append(bank.getCode().getTITLE());
        for (int i = 0; i < 20; i++) {
            iban.append(random.nextInt(10));
        }
        account.setIban(String.valueOf(iban));
        return accountDao.create(account);
    }

    public List<AccountEntity> findAllByClient(Long id, String bank) {
        return accountDao.findAllByClient(id, bank);
    }

    public List<AccountEntity> findAll() {
        return accountDao.findAll();
    }

    public Optional<AccountEntity> findById(Long id) {
        return accountDao.findById(id);
    }

    public Optional<AccountEntity> update(AccountEntity account) {
        return accountDao.update(account);
    }

    public Optional<AccountEntity> findByIban(String iban) {
        return accountDao.findByIban(iban);
    }

    public void deposit(TransactionEntity transaction) {
        Double balanceSendAccount = transaction.getAccountSend().getBalance();
        if (transaction.getAmount() != null && balanceSendAccount != null) {
            transaction.getAccountSend().setBalance(balanceSendAccount + transaction.getAmount());
        }
    }

    public void deleteById(Long id) {
        accountDao.deleteById(id);
    }

    public boolean transfer(TransactionEntity transaction) {
        Double balanceSendAccount = transaction.getAccountSend().getBalance();
        Double balanceRecipientAccount = transaction.getAccountRecipient().getBalance();
        if (transaction.getAmount() != null
               && balanceSendAccount != null
                && balanceRecipientAccount != null) {
            transaction.getAccountSend().setBalance(balanceSendAccount - transaction.getAmount());
            transaction.getAccountRecipient().setBalance(balanceRecipientAccount + transaction.getAmount());
            return true;
        }
        return false;
    }

    public boolean withdraw(TransactionEntity transaction) {
        Double balanceSendAccount = transaction.getAccountSend().getBalance();
        if (transaction.getAmount() != null && balanceSendAccount != null) {
            if (balanceSendAccount >= transaction.getAmount()) {
                transaction.getAccountSend().setBalance(balanceSendAccount - transaction.getAmount());
            } else {
                return false;
            }
        }
        return true;
    }

    public static AccountService getInstance() {
        return INSTANCE;
    }
}
