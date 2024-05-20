package tech.elsoft.maidcclibrarymanagementsystem.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.LoginDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.RegisterLibrarianDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.TokenResponse;

import java.io.IOException;

public interface AuthService {
    ApiResponse register(RegisterLibrarianDTO request);

    ApiResponse login(LoginDTO loginDTO);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException;
}
