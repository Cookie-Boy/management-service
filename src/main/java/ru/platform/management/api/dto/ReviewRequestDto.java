package ru.platform.management.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {

    @NotNull(message = "doctorId is required")
    private UUID doctorId;

    @NotBlank(message = "authorName is required")
    @Size(max = 100, message = "authorName max length 100")
    private String authorName;

    @NotNull(message = "rating is required")
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank(message = "comment is required")
    @Size(max = 2000, message = "comment max length 2000")
    private String comment;
}