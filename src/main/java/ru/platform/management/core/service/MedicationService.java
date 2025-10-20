package ru.platform.management.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.api.mapper.MedicationMapper;
import ru.platform.management.core.model.entity.Medication;
import ru.platform.management.core.model.entity.Order;
import ru.platform.management.core.repository.MedicationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;
    private final OrderService orderService;

    @Transactional
    public Medication updateStock(UUID id, int quantity) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Препарат с UUID = '" + id + "' не найден"));

        int newQuantity = quantity + medication.getQuantityInStock();
        medication.setQuantityInStock(newQuantity);

        return medicationRepository.save(medication);
    }

    @Scheduled(cron = "0 30 2 * * ?")
    public void checkAllMedicationsStock() {
        List<Medication> medications = medicationRepository.findAll();

        medications.forEach(medication -> {
            if (medication.getQuantityInStock() <= medication.getMinStockLevel()) {
                int quantity = calculateOrderQuantity(medication);
                BigDecimal totalPrice = medication.getPricePerUnit().multiply(new BigDecimal(quantity));
                Order order = new Order(medication, LocalDateTime.now(), quantity, totalPrice, "PENDING");
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
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    public List<MedicationResponseDto> getAllMedicines() {
        List<Medication> medicines = medicationRepository.findAll();
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
