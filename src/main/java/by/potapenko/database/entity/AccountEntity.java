package by.potapenko.database.entity;


import by.potapenko.database.enam.TypeAccount;
import by.potapenko.database.enam.TypeCurrency;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class AccountEntity implements BaseIdEntity<Long> {
    private Long id;
    private TypeAccount account;
    private TypeCurrency currency;
    private String iban;
    private UserEntity owner;
    private BankEntity bank;
    private Double balance;
    private Double interestAmount;

    @Builder.Default
    private LocalDate dateOfCreation = LocalDate.now();
    @Builder.Default
    private List<TransactionEntity> transactions = new ArrayList<>();

    public void addUser(UserEntity user) {
        user.getAccounts().add(this);
    }

    public void addTransaction(TransactionEntity transaction) {
        this.getTransactions().add(transaction);
    }
}
