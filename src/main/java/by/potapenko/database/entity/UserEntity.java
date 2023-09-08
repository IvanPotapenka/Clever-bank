package by.potapenko.database.entity;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class UserEntity implements BaseIdEntity<Long> {
    private Long id;
    private String fullName;
    private String email;
    private String password;

    @Builder.Default
    private List<AccountEntity> accounts = new ArrayList<>();
    @Builder.Default
    private List<BankEntity> banks = new ArrayList<>();

}
