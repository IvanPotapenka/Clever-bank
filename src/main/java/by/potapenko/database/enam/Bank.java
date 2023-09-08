package by.potapenko.database.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Bank {
    CLEVER("Clever-Bank"),
    IRON("Iron-Bank"),
    ALFA("Alfa-Bank"),
    VTB("Vtb-Bank"),
    TECHNO("Techno-Bank");
    private final String title;
}
