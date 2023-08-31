package by.potapenko.database.entity;

import by.potapenko.database.enam.Bank;
import by.potapenko.database.enam.CodeBank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class BankEntity implements BaseIdEntity<Long> {
    private Long id;
    private CodeBank code;
    private Bank bankName;

    @Builder.Default
    private List<UserEntity> user = new ArrayList<>();

    public void addUser(UserEntity user) {
        this.user.add(user);
        user.getBanks().add(this);
    }
}
