package by.potapenko.database.dao;

import by.potapenko.connection.ConnectionPool;
import by.potapenko.database.enam.Bank;
import by.potapenko.database.enam.CodeBank;
import by.potapenko.database.entity.BankEntity;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class BankDao {

    private static final BankDao INSTANCE = new BankDao();

    private static final String INSERT_BANK = "INSERT INTO bank(name, code) VALUES (?,?)";

    private static final String SELECT_BY_BANK = "SELECT * FROM bank WHERE name=?";

    private static final String SELECT_ALL = "SELECT * FROM bank";

    private static final String DELETE_BY_ID = "DELETE FROM bank WHERE id=?";

    private static final String UPDATE_BANK = "UPDATE bank SET name=?, code=? WHERE  id=?";

    public Optional<BankEntity> create(BankEntity bank) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_BANK, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bank.getBankName().getTitle());
            preparedStatement.setString(2, bank.getCode().getTitle());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                bank.setId(resultSet.getLong("id"));
            }
            return Optional.of(bank);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<BankEntity> findByBank(String bankName) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_BANK)) {
            preparedStatement.setString(1, bankName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(BankEntity.builder()
                    .bankName(Bank.valueOf(resultSet.getString("name")))
                    .code(CodeBank.valueOf(resultSet.getString("code")))
                    .id(resultSet.getLong("id"))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public List<BankEntity> findAll() {
        List<BankEntity> bankList = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankList.add(BankEntity.builder()
                        .id(resultSet.getLong("id"))
                        .bankName(Bank.valueOf(resultSet.getString("name")))
                        .code(CodeBank.valueOf(resultSet.getString("code")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankList;
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

    public Optional<BankEntity> update(BankEntity bank) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BANK)) {
            preparedStatement.setString(1, bank.getBankName().getTitle());
            preparedStatement.setString(2, bank.getCode().getTitle());
            preparedStatement.setLong(3, bank.getId());
            preparedStatement.executeUpdate();
            return Optional.of(bank);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static BankDao getInstance() {
        return INSTANCE;
    }
}

