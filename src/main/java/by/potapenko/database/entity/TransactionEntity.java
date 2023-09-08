package by.potapenko.database.entity;

import by.potapenko.database.enam.TypeTransaction;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class TransactionEntity implements BaseIdEntity<Long> {
    private Long id;
    private TypeTransaction type;
    private Double amount;
    private AccountEntity accountSend;
    private AccountEntity accountRecipient;
    @Builder.Default
    private LocalDate date = LocalDate.parse(LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    @Builder.Default
    private LocalTime time = LocalTime.parse(LocalTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
}
