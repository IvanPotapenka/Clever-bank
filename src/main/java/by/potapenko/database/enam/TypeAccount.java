package by.potapenko.database.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeAccount {
    CARD("Расчётный"),
    DEPOSIT("Сберегательный");
    private final String TITLE;
}
