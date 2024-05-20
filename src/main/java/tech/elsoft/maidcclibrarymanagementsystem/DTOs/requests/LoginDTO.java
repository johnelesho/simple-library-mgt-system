package tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record LoginDTO (@NotBlank String usernameOrEmail,@NotBlank String password)
{
}
