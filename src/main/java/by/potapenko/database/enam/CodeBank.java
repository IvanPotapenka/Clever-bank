package by.potapenko.database.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodeBank {
    CLEVBY2X("CLEV"),
    IRONBY2X("IRON"),
    NBRBBY2X("NBRB"),
    TECNBY22("TECN"),
    ALFABY2X("ALFA");

    private final String TITLE;
}

