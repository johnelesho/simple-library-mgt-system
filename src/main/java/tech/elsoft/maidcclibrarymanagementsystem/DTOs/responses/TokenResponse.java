package tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
}
