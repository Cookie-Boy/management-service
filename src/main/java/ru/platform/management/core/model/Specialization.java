package ru.platform.management.core.model;

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
}
