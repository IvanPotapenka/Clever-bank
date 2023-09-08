package by.potapenko.database.dao;

import by.potapenko.connection.ConnectionPool;
import by.potapenko.database.enam.TypeTransaction;
import by.potapenko.database.entity.TransactionEntity;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TransactionDao {

    private static final TransactionDao INSTANCE = new TransactionDao();

    private static final String INSERT_TRANSACTION = "INSERT INTO transaction(type, amount, account_send_id, account_recipient_id,  date, time ) VALUES (?,?,?,?,?,?)";

    private static final String UPDATE_ACCOUNT = "UPDATE account SET balance=? WHERE id=?";

    private static final String SELECT_ALL = "SELECT * FROM transaction";

    private static final String DELETE_BY_ID = "DELETE FROM transaction WHERE id=?";

    private static final String UPDATE_TRANSACTION = "UPDATE transaction SET type=?, amount=?, account_send_id=?, account_recipient_id=?,  date=?, time=? WHERE  id=?";

    private static final String SELECT_ALL_BY_ACCOUNT = "SELECT * FROM transaction JOIN account a ON a.id = transaction.account_send_id WHERE account_send_id = ?";

    private static final String SELECT_ALL_BY_ACCOUNT_AND_DATE = "SELECT * FROM transaction JOIN account a ON a.id = transaction.account_send_id WHERE account_send_id = ? AND date BETWEEN ? AND ?";

    @SneakyThrows
    public Optional<TransactionEntity> create(TransactionEntity transaction) {
        Connection connection = ConnectionPool.open();
        try (PreparedStatement preparedStatementAccountSend = connection.prepareStatement(UPDATE_ACCOUNT);
             PreparedStatement preparedStatementAccountRecipient = connection.prepareStatement(UPDATE_ACCOUNT);
             PreparedStatement preparedStatementTransaction = connection.prepareStatement(
                     INSERT_TRANSACTION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatementTransaction.setString(1, String.valueOf(transaction.getType()));
            preparedStatementTransaction.setDouble(2, transaction.getAmount());
            preparedStatementTransaction.setLong(3, transaction.getAccountSend().getId());
            preparedStatementTransaction.setLong(4, transaction.getAccountRecipient().getId());
            preparedStatementTransaction.setString(5, String.valueOf(transaction.getDate()));
            preparedStatementTransaction.setString(6, String.valueOf(transaction.getTime()));

            preparedStatementAccountSend.setDouble(1, transaction.getAccountSend().getBalance());
            preparedStatementAccountSend.setLong(2, transaction.getAccountSend().getId());

            preparedStatementAccountRecipient.setDouble(1, transaction.getAccountRecipient().getBalance());
            preparedStatementAccountRecipient.setLong(2, transaction.getAccountRecipient().getId());

            connection.setAutoCommit(false);
            preparedStatementAccountSend.executeUpdate();
            preparedStatementAccountRecipient.executeUpdate();
            preparedStatementTransaction.executeUpdate();
            ResultSet resultSet = preparedStatementTransaction.getGeneratedKeys();
            if (resultSet.next()) {
                transaction.setId(resultSet.getLong("id"));
            }
            connection.commit();
            return Optional.of(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return Optional.empty();
        } finally {
            connection.close();
        }
    }

    public List<TransactionEntity> findAll() {
        List<TransactionEntity> transactionList = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactionList.add(TransactionEntity.builder()
                        .id(resultSet.getLong("id"))
                        .type(TypeTransaction.valueOf(resultSet.getString("type")))
                        .amount(resultSet.getDouble("amount"))
                        .date(LocalDate.parse(resultSet.getString("date")))
                        .time(LocalTime.parse(resultSet.getString("time")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
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

    public Optional<TransactionEntity> update(TransactionEntity transaction) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSACTION)) {
            preparedStatement.setString(1, String.valueOf(transaction.getType()));
            preparedStatement.setDouble(2, transaction.getAmount());
            preparedStatement.setLong(3, transaction.getAccountSend().getId());
            preparedStatement.setLong(4, transaction.getAccountRecipient().getId());
            preparedStatement.setString(5, String.valueOf(transaction.getDate()));
            preparedStatement.setString(6, String.valueOf(transaction.getTime()));
            preparedStatement.executeUpdate();
            return Optional.of(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public List<TransactionEntity> findAllByAccount(Long id) {
        List<TransactionEntity> transactions = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_ACCOUNT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(TransactionEntity.builder()
                        .id(resultSet.getLong("id"))
                        .type(TypeTransaction.valueOf(resultSet.getString("type")))
                        .amount(resultSet.getDouble("amount"))
                        .date(LocalDate.parse(resultSet.getString("date")))
                        .time(LocalTime.parse(resultSet.getString("time")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<TransactionEntity> findAllByAccountAndDate(Long id, String dateFrom, String dateTo) {
        List<TransactionEntity> transactions = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_ACCOUNT_AND_DATE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, dateFrom);
            preparedStatement.setString(3, dateTo);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(TransactionEntity.builder()
                        .id(resultSet.getLong("id"))
                        .type(TypeTransaction.valueOf(resultSet.getString("type")))
                        .amount(resultSet.getDouble("amount"))
                        .date(LocalDate.parse(resultSet.getString("date")))
                        .time(LocalTime.parse(resultSet.getString("time")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static TransactionDao getInstance() {
        return INSTANCE;
    }
}
