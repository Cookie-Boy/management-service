package ru.platform.management.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.core.service.MedicationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicationController.class)
public class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MedicationService medicationService;

    private final String BASE_URL = "/medicines";
    private final UUID TEST_ID = UUID.fromString("7e4234b3-d198-47d4-95fb-07f81fe4de06");

    private MedicationRequestDto createTestRequestDto() {
        return new MedicationRequestDto(
                "Аспирин",
                "Обезболивающее средство",
                "Bayer",
                "BATCH-12345",
                LocalDate.of(2025, 12, 31),
                100,
                20,
                50,
                new BigDecimal("15.99"),
                false
        );
    }

    private MedicationResponseDto createTestResponseDto() {
        return new MedicationResponseDto(
                TEST_ID,
                "Аспирин",
                "Обезболивающее средство",
                "Bayer",
                "BATCH-12345",
                LocalDate.of(2025, 12, 31),
                100,
                20,
                50,
                new BigDecimal("15.99"),
                false
        );
    }

    @Test
    void createMedication_ShouldReturnCreatedStatus() throws Exception {
        MedicationRequestDto requestDto = createTestRequestDto();
        MedicationResponseDto responseDto = createTestResponseDto();

        given(medicationService.createMedication(any(MedicationRequestDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(TEST_ID.toString())))
                .andExpect(jsonPath("$.name", is("Аспирин")))
                .andExpect(jsonPath("$.pricePerUnit", is(15.99)));
    }

    @Test
    void getAllMedicines_ShouldReturnOkStatus() throws Exception {
        MedicationResponseDto responseDto = createTestResponseDto();

        given(medicationService.getAllMedicines())
                .willReturn(List.of(responseDto));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Аспирин")));
    }

    @Test
    void getMedication_WhenExists_ShouldReturnMedication() throws Exception {
        MedicationResponseDto responseDto = createTestResponseDto();

        given(medicationService.getMedicationById(eq(TEST_ID)))
                .willReturn(responseDto);

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TEST_ID.toString())))
                .andExpect(jsonPath("$.batchNumber", is("BATCH-12345")));
    }

    @Test
    void getMedication_WhenNotExists_ShouldReturnNotFound() throws Exception {
        given(medicationService.getMedicationById(eq(TEST_ID)))
                .willThrow(new EntityNotFoundException("Лекарство не найдено"));

        mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("не найдено")));
    }

    @Test
    void updateMedication_ShouldReturnUpdatedMedication() throws Exception {
        MedicationRequestDto requestDto = createTestRequestDto();
        MedicationResponseDto responseDto = createTestResponseDto();

        given(medicationService.updateMedicationById(eq(TEST_ID), any(MedicationRequestDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(patch(BASE_URL + "/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manufacturer", is("Bayer")))
                .andExpect(jsonPath("$.isPrescriptionOnly", is(false)));
    }

    @Test
    void deleteMedication_ShouldReturnSuccessResponse() throws Exception {
        SuccessResponseDto successResponse = new SuccessResponseDto("Лекарство удалено");

        given(medicationService.deleteMedicationById(eq(TEST_ID)))
                .willReturn(successResponse);

        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", containsString("удалено")));
    }
}