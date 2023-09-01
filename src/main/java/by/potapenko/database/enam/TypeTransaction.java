package by.potapenko.database.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeTransaction {
    REPLENISHMENT("Пополнение"),
    WITHDRAWAL("Снятие средств"),
    TRANSFER("Перевод");
    private final String title;
}
