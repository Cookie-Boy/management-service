package ru.platform.management.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.*;
import ru.platform.management.api.mapper.MedicationMapper;
import ru.platform.management.core.model.entity.Clinic;
import ru.platform.management.core.model.entity.Doctor;
import ru.platform.management.core.model.entity.Medication;
import ru.platform.management.core.model.entity.Order;
import ru.platform.management.core.repository.jpa.ClinicRepository;
import ru.platform.management.core.repository.jpa.MedicationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;
    private final OrderService orderService;
    private final ClinicRepository clinicRepository;

    @Transactional
    public void updateStock(UUID id, int quantity) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Препарат с UUID = '" + id + "' не найден"));

        int newQuantity = quantity + medication.getQuantityInStock();
        medication.setQuantityInStock(newQuantity);

        medicationRepository.save(medication);
    }

    @Scheduled(fixedDelay = 100_000)
    public void checkAllMedicationsStock() {
        List<Medication> medications = medicationRepository.findAll();
        log.info("check medications quantity: {}", medications.size());

        medications.forEach(medication -> {
            if (medication.getQuantityInStock() <= medication.getMinStockLevel()) {
                int quantity = calculateOrderQuantity(medication);
                BigDecimal totalPrice = medication.getPricePerUnit()
                        .multiply(BigDecimal.valueOf(quantity));

                Order order = new Order(
                        medication,
                        LocalDateTime.now(),
                        quantity,
                        totalPrice,
                        "PENDING"
                );
                orderService.createOrder(order);
            }
        });
    }

    private int calculateOrderQuantity(Medication medication) {
        int deficit = medication.getMinStockLevel() - medication.getQuantityInStock();
        return Math.max(deficit, medication.getReorderQuantity());
    }

    @Transactional
    public MedicationResponseDto createMedication(MedicationRequestDto medicationDto) {
        Medication medication = medicationMapper.toEntity(medicationDto);

        Clinic clinic = clinicRepository.findById(UUID.fromString(medicationDto.clinicId()))
                .orElseThrow(() -> new EntityNotFoundException("Клиника не найдена"));

        medication.setClinic(clinic);

        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    public List<MedicationResponseDto> getAllMedicines() {
        List<Medication> medicines = medicationRepository.findAll();
        return medicationMapper.toDto(medicines);
    }

    public List<MedicationResponseDto> getMedicationsByClinicId(UUID clinicId) {
        List<Medication> medicines = medicationRepository.findAllByClinicId(clinicId);
        return medicationMapper.toDto(medicines);
    }

    public MedicationResponseDto getMedicationById(UUID id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лекарство с ID = " + id + " не найдено."));
        return medicationMapper.toDto(medication);
    }

    @Transactional
    public MedicationResponseDto updateMedicationById(UUID id, MedicationRequestDto medicationDto) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лекарство с UUID = '" + id + "' не найдено."));

        medicationMapper.updateMedicationFromDto(medicationDto, medication);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    @Transactional
    public SuccessResponseDto deleteMedicationById(UUID id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лекарство с UUID = '" + id + "' не найдено."));
        medicationRepository.delete(medication);
        return new SuccessResponseDto("Лекарство с UUID = '" + id + "' успешно удалено.");
    }
}
