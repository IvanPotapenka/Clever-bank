package by.potapenko.database.entity;

import java.io.Serializable;

public interface BaseIdEntity<T extends Serializable> {
    T getId();

    void setId(T id);
}