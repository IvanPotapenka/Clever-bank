package by.potapenko.database.dao;

import by.potapenko.connection.ConnectionPool;
import by.potapenko.database.entity.UserEntity;
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
public final class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    private static final String INSERT_USER = "INSERT INTO client(full_name, email, password) VALUES (?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM client ORDER BY full_name";
    private static final String UPDATE_USER = "UPDATE client SET full_name=?, email=?, password=? WHERE id=?";
    private static final String DELETE_BY_ID = "DELETE FROM client WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM client WHERE id=?";
    private static final String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM bank_client JOIN bank b ON b.id = bank_client.bank_id JOIN client c ON c.id = bank_client.client_id WHERE c.email=? AND c.password=? AND b.name=?";
    private static final String SELECT_BY_EMAIL = "SELECT client.email FROM client WHERE email=?";
    private static final String INSERT_BANK_CLIENT = "INSERT INTO bank_client(bank_id, client_id) VALUES (?,?)";

    public Optional<UserEntity> create(UserEntity user) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void createBankClient(Long bankId, Long clientId) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_BANK_CLIENT)) {
            preparedStatement.setLong(1, bankId);
            preparedStatement.setLong(2, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserEntity> findAll() {
        List<UserEntity> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(UserEntity.builder()
                        .id(resultSet.getLong("id"))
                        .fullName(resultSet.getString("full_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public Optional<UserEntity> update(UserEntity user) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            return Optional.of(user);
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

    public Optional<UserEntity> findById(Long id) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(UserEntity.builder()
                    .fullName(resultSet.getString("full_name"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<UserEntity> findByEmail(String email) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(UserEntity.builder()
                    .email(resultSet.getString("email"))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<UserEntity> findByEmailAndPassword(String email, String password, String bankName) {
        try (Connection connection = ConnectionPool.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, bankName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(UserEntity.builder()
                    .fullName(resultSet.getString("full_name"))
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}

