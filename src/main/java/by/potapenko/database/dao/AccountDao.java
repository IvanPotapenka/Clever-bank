package by.potapenko.database.dao;

import by.potapenko.connection.ConnectionPool;
import by.potapenko.database.enam.Bank;
import by.potapenko.database.enam.TypeAccount;
import by.potapenko.database.enam.TypeCurrency;
import by.potapenko.database.entity.AccountEntity;
import by.potapenko.database.entity.BankEntity;
import by.potapenko.database.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AccountDao {
    private static final AccountDao INSTANCE = new AccountDao();
    private static final String INSERT_ACCOUNT = "INSERT INTO account(account_type,currency,iban,client_id,bank_id,balance, interest_amount, date_of_creation) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_BY_CLIENT = "SELECT * FROM account JOIN bank b ON b.id = account.bank_id WHERE client_id= ? AND b.name=?";
    private static final String SELECT_ALL = "SELECT * FROM account";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET balance=?, interest_amount=? WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM account JOIN bank b ON b.id = account.bank_id JOIN client c ON c.id = account.client_id WHERE account.id=?;";
    private static final String SELECT_BY_IBAN = "SELECT * FROM account JOIN bank b ON b.id = account.bank_id WHERE iban=?";
    private static final String DELETE_BY_ID = "DELETE FROM account WHERE id=?";

    public Optional<AccountEntity> create(AccountEntity account) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(account.getAccount()));
            preparedStatement.setString(2, String.valueOf(account.getCurrency()));
            preparedStatement.setString(3, account.getIban());
            preparedStatement.setLong(4, account.getOwner().getId());
            preparedStatement.setLong(5, account.getBank().getId());
            preparedStatement.setDouble(6, account.getBalance());
            preparedStatement.setDouble(7, account.getInterestAmount());
            preparedStatement.setString(8, String.valueOf(account.getDateOfCreation()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                account.setId(resultSet.getLong("id"));
            }
            return Optional.of(account);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<AccountEntity> findAllByClient(Long id, String bank) {
        List<AccountEntity> accountList = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_CLIENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, bank);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(AccountEntity.builder()
                        .id(resultSet.getLong("id"))
                        .account(TypeAccount.valueOf(resultSet.getString("account_type")))
                        .currency(TypeCurrency.valueOf(resultSet.getString("currency")))
                        .balance(resultSet.getDouble("balance"))
                        .interestAmount(resultSet.getDouble("interest_amount"))
                        .bank(BankEntity.builder()
                                .bankName(Bank.valueOf(resultSet.getString("name")))
                                .build())
                        .iban(resultSet.getString("iban"))
                        .dateOfCreation(LocalDate.parse(resultSet.getString("date_of_creation")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public List<AccountEntity> findAll() {
        List<AccountEntity> accountList = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(AccountEntity.builder()
                        .id(resultSet.getLong("id"))
                        .account(TypeAccount.valueOf(resultSet.getString("account_type")))
                        .currency(TypeCurrency.valueOf(resultSet.getString("currency")))
                        .balance(resultSet.getDouble("balance"))
                        .interestAmount(resultSet.getDouble("interest_amount"))
                        .iban(resultSet.getString("iban"))
                        .dateOfCreation(LocalDate.parse(resultSet.getString("date_of_creation")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public Optional<AccountEntity> update(AccountEntity account) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setDouble(2, account.getInterestAmount());
            preparedStatement.setLong(3, account.getId());
            preparedStatement.executeUpdate();
            return Optional.of(account);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<AccountEntity> findById(Long id) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                   ? Optional.of(AccountEntity.builder()
                            .id(resultSet.getLong("id"))
                            .iban(resultSet.getString("iban"))
                            .account(TypeAccount.valueOf(resultSet.getString("account_type")))
                            .balance(resultSet.getDouble("balance"))
                            .bank(BankEntity.builder()
                                    .bankName(Bank.valueOf(resultSet.getString("name")))
                                    .build())
                            .owner(UserEntity.builder()
                                    .fullName(resultSet.getString("full_name"))
                                    .build())
                            .currency(TypeCurrency.valueOf(resultSet.getString("currency")))
                            .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<AccountEntity> findByIban(String iban) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_IBAN)) {
            preparedStatement.setString(1, iban);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(AccountEntity.builder()
                            .id(resultSet.getLong("id"))
                            .iban(resultSet.getString("iban"))
                            .account(TypeAccount.valueOf(resultSet.getString("account_type")))
                            .balance(resultSet.getDouble("balance"))
                            .bank(BankEntity.builder()
                                    .bankName(Bank.valueOf(resultSet.getString("name")))
                                    .build())
                            .currency(TypeCurrency.valueOf(resultSet.getString("currency")))
                            .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static AccountDao getInstance() {
        return INSTANCE;
    }
}
