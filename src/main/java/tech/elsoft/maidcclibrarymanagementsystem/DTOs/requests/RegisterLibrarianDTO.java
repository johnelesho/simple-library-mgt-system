package tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.enums.LibraryUserType;

public record RegisterLibrarianDTO(

        @NotBlank
        String password,
        @NotBlank
        String username,
        @NotNull
        LibraryUserType libraryUserType,

        @Email
        String email,
        @NotBlank String fullName) {
}
