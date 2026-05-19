package ru.platform.management.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Specialization {
    GENERAL_PRACTITIONER("Терапевт"),
    SURGEON("Хирург"),
    DENTIST("Стоматолог"),
    DERMATOLOGIST("Дерматолог"),
    OPHTHALMOLOGIST("Офтальмолог"),
    CARDIOLOGIST("Кардиолог"),
    ONCOLOGIST("Онколог"),
    EXOTIC_PETS("Экзотические животные"),
    FELINE("Кошки"),
    CANINE("Собаки");

    private final String name;

    Specialization(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Specialization fromName(String name) {
        for (Specialization specialization : Specialization.values()) {
            if (specialization.name.equalsIgnoreCase(name)) {
                return specialization;
            }
        }
        throw new IllegalArgumentException("Неизвестная специализация: " + name);
    }
}
