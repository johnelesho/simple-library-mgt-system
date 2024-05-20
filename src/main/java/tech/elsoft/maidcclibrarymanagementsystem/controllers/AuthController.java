package tech.elsoft.maidcclibrarymanagementsystem.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.LoginDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.RegisterLibrarianDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.TokenResponse;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Module")
public class AuthController {

final AuthService authService;
final LogoutHandler logoutHandler;

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginDTO authRequest) {
     return   ResponseEntity.ok(authService.login(authRequest));
    }   @PostMapping("logout")
    @SecurityRequirement(name = "lms")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request,
                              HttpServletResponse response,
                              Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
        return ResponseEntity.ok(new ApiResponse());
    }
    @PostMapping("register")
    public ResponseEntity<ApiResponse> registerLibarian(@RequestBody @Valid RegisterLibrarianDTO authRequest) {
     return  ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authRequest));
    }
    @PostMapping("/refresh-token")
    @SecurityRequirement(name = "lms")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TokenResponse tokenResponse =
                authService.refreshToken(request, response);
//        return ResponseEntity.ok(new ApiResponse(tokenResponse));
    }
}
