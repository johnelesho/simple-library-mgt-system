package tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreatePatronDTO (
        @NotBlank
        String fullName,

@NotBlank
        @Email
String email,
        @NotBlank
        String streetAddress,
       @NotBlank
        String city,
        @NotBlank
        String stateOrProvince,
        @NotBlank
        String postalCode,
         @NotBlank
        String username,

                               boolean activeSubscription,
        @NotBlank
        String country) {
}
