package by.potapenko.service;

import by.potapenko.database.dao.UserDao;
import by.potapenko.database.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserService {

    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    public Optional<UserEntity> create(UserEntity user) {
        return userDao.create(user);
    }

    public void createBankClient(Long bankId, Long clientId) {
        userDao.createBankClient(bankId, clientId);
    }

    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    public Optional<UserEntity> update(UserEntity user) {
        return userDao.update(user);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public Optional<UserEntity> findById(Long id) {
        return userDao.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Optional<UserEntity> findByEmailAndPassword(String email, String password, String bankName) {
        return userDao.findByEmailAndPassword(email, password, bankName);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}

