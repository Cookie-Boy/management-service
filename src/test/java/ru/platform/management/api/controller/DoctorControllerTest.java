package ru.platform.management.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.platform.management.api.dto.DoctorRequestDto;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.core.model.Specialization;
import ru.platform.management.core.service.DoctorService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoctorService doctorService;

    private final String url = "/doctors";

    @Test
    void createDoctor_ShouldReturnCreatedDoctor() throws Exception {
        String doctorJson = """
            {
                "firstName": "Иван",
                "lastName": "Петров",
                "licenseNumber": "LIC-12345",
                "phoneNumber": "+79161234567",
                "email": "ivan.petrov@example.com",
                "specialization": "Терапевт",
                "hireDate": "2018-05-20",
                "startWorkingDay": "08:00",
                "endWorkingDay": "16:00",
                "bio": "I am Ivan Petrov. Here are some test information about me."
            }
            """;

        DoctorResponseDto createdDoctor = DoctorResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName("Иван")
                .lastName("Петров")
                .middleName("Петрович")
                .specialization(Specialization.GENERAL_PRACTITIONER)
                .licenseNumber("LIC-12345")
                .phoneNumber("+79161234567")
                .email("ivan.petrov@example.com")
                .hireDate(LocalDate.of(2018, 5, 20))
                .startWorkingDay(LocalTime.of(8, 0))
                .endWorkingDay(LocalTime.of(16, 0))
                .bio("I am Ivan Petrov. Here are some test information about me.")
                .build();


        given(doctorService.createDoctor(any(DoctorRequestDto.class)))
                .willReturn(createdDoctor);

        ResultActions performedRequest = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorJson))
                .andExpect(status().isCreated());
        checkJsonFields(performedRequest, createdDoctor);
    }

    @Test
    void createDoctor_WithoutOptionalFields() throws Exception {
        String doctorJson = """
            {
                "firstName": "Иван",
                "lastName": "Петров",
                "licenseNumber": "LIC-12345",
                "phoneNumber": "+79161234567",
                "email": "ivan.petrov@example.com",
                "specialization": "Терапевт"
            }
            """;

        DoctorResponseDto createdDoctor = DoctorResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName("Иван")
                .lastName("Петров")
                .specialization(Specialization.GENERAL_PRACTITIONER)
                .licenseNumber("LIC-12345")
                .phoneNumber("+79161234567")
                .email("ivan.petrov@example.com")
                .hireDate(LocalDate.now())
                .startWorkingDay(LocalTime.of(10, 0))
                .endWorkingDay(LocalTime.of(18, 0))
                .build();

        given(doctorService.createDoctor(any(DoctorRequestDto.class)))
                .willReturn(createdDoctor);

        ResultActions performedRequest = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorJson))
                .andExpect(status().isCreated());
        checkJsonFields(performedRequest, createdDoctor);
    }

    @Test
    void createDoctor_ShouldReturnBadRequest() throws Exception {
        String invalidDoctorJson = """
            {
                "firstName": "Иван",
                "lastName": "Петров",
                "licenseNumber": "some_wrong_license_number",
                "phoneNumber": "+79161234567",
                "email": "invalid_email",
                "specialization": "Терапевт"
            }
            """;

        given(doctorService.createDoctor(any(DoctorRequestDto.class)))
                .willThrow(new ConstraintViolationException("Валидация некоторых полей не прошла.", Set.of()));

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDoctorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Валидация некоторых полей не прошла."));
    }

    @Test
    void getDoctorById_ShouldReturnDoctor() throws Exception {
        UUID doctorId = UUID.randomUUID();
        DoctorResponseDto doctor = DoctorResponseDto.builder()
                .id(doctorId)
                .firstName("Иван")
                .lastName("Петров")
                .specialization(Specialization.GENERAL_PRACTITIONER)
                .licenseNumber("LIC-12345")
                .phoneNumber("+79161234567")
                .email("ivan.petrov@example.com")
                .hireDate(LocalDate.now())
                .startWorkingDay(LocalTime.of(10, 0))
                .endWorkingDay(LocalTime.of(18, 0))
                .build();

        given(doctorService.getDoctorById(doctorId))
                .willReturn(doctor);

        ResultActions performedRequest = mockMvc.perform(get(url + "/{id}", doctorId))
                .andExpect(status().isOk());
        checkJsonFields(performedRequest, doctor);
    }

    @Test
    void getDoctorById_ShouldReturnNotFound() throws Exception {
        UUID doctorId = UUID.randomUUID();

        given(doctorService.getDoctorById(doctorId))
                .willThrow(new EntityNotFoundException("Доктор с таким UUID не найден."));

        mockMvc.perform(get(url + "/{id}", doctorId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Доктор с таким UUID не найден."));
    }

    @Test
    void updateDoctor_ShouldReturnUpdatedDoctor() throws Exception {
        UUID doctorId = UUID.randomUUID();
        String updateJson = """
            {
                "firstName": "Виктор",
                "lastName": "Асафьев"
            }
            """;

        DoctorResponseDto updatedDoctor = DoctorResponseDto.builder()
                .id(doctorId)
                .firstName("Виктор")
                .lastName("Асафьев")
                .middleName("Петрович")
                .specialization(Specialization.GENERAL_PRACTITIONER)
                .licenseNumber("LIC-12345")
                .phoneNumber("+79161234567")
                .email("ivan.petrov@example.com")
                .hireDate(LocalDate.of(2018, 5, 20))
                .startWorkingDay(LocalTime.of(10, 0))
                .endWorkingDay(LocalTime.of(18, 0))
                .bio("I am Ivan Petrov. Here are some test information about me.")
                .build();

        given(doctorService.updateDoctorById(eq(doctorId), any(DoctorRequestDto.class)))
                .willReturn(updatedDoctor);

        ResultActions performedRequest = mockMvc.perform(patch(url + "/{id}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk());
        checkJsonFields(performedRequest, updatedDoctor);
    }

    @Test
    void updateDoctor_ShouldReturnNotFound() throws Exception {
        UUID doctorId = UUID.randomUUID();
        String updateJson = """
            {
                "firstName": "Виктор",
                "lastName": "Асафьев"
            }
            """;

        given(doctorService.updateDoctorById(eq(doctorId), any(DoctorRequestDto.class)))
                .willThrow(new EntityNotFoundException("Доктор с таким UUID не найден."));

        mockMvc.perform(patch(url + "/{id}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Доктор с таким UUID не найден."));
    }

    @Test
    void deleteDoctor_ShouldReturnSuccessResponse() throws Exception {
        UUID doctorId = UUID.randomUUID();

        given(doctorService.deleteDoctorById(doctorId))
                .willReturn(new SuccessResponseDto("Доктор с ID '" + doctorId + "' успешно удален."));

        mockMvc.perform(delete(url + "/{id}", doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Доктор с ID '" + doctorId + "' успешно удален."));
    }

    void checkJsonFields(ResultActions performedRequest, DoctorResponseDto response) throws Exception {
        performedRequest.andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.firstName").value(response.firstName()))
                .andExpect(jsonPath("$.lastName").value(response.lastName()))
                .andExpect(jsonPath("$.middleName").value(response.middleName()))
                .andExpect(jsonPath("$.specialization").value(response.specialization().getName()))
                .andExpect(jsonPath("$.licenseNumber").value(response.licenseNumber()))
                .andExpect(jsonPath("$.phoneNumber").value(response.phoneNumber()))
                .andExpect(jsonPath("$.email").value(response.email()))
                .andExpect(jsonPath("$.hireDate").value(response.hireDate().toString()))
                .andExpect(jsonPath("$.startWorkingDay").value(containsString(response.startWorkingDay().toString())))
                .andExpect(jsonPath("$.endWorkingDay").value(containsString(response.endWorkingDay().toString())))
                .andExpect(jsonPath("$.bio").value(response.bio()));
    }
}
