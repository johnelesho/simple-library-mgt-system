package tech.elsoft.maidcclibrarymanagementsystem.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.enums.TokenType;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.LoginDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.RegisterLibrarianDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.TokenResponse;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Librarian;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.LibrarianToken;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.LibrarianRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.LibrarianTokenRepository;
import tech.elsoft.maidcclibrarymanagementsystem.security.JwtService;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.AuthService;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.LibrarianService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    final JwtService jwtService;

    final AuthenticationManager authenticationManager;
    final LibrarianTokenRepository tokenRepository;

    final LibrarianService librarianService;
    final LibrarianRepository librarianRepository;
    @Override
    public ApiResponse register(RegisterLibrarianDTO request){
       return new ApiResponse(librarianService.createNew(request));
    }

    @Override
    public ApiResponse login(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginDTO.usernameOrEmail() , loginDTO.password()));
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Login Failed");
        }
        var librarian = librarianRepository.findByEmailOrUsername(loginDTO.usernameOrEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(librarian);
        var refreshToken = jwtService.generateRefreshToken(librarian);
        revokeAllUserTokens(librarian);
        saveUserToken(librarian, jwtToken);
        TokenResponse response = TokenResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return new ApiResponse(response);

    }

    private void saveUserToken(Librarian librarian, String jwtToken) {
        var token = LibrarianToken.builder()
                .librarian(librarian)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Librarian librarian) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(librarian.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new SignatureException("Could not validate token");
        }
            var user = librarianRepository.findByEmailOrUsername(userEmail)
                    .orElseThrow();

            if (!jwtService.isTokenValid(refreshToken, user))
                throw new InvalidBearerTokenException("Could not validate token");

        var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                TokenResponse token = TokenResponse.builder().refreshToken(refreshToken).accessToken(accessToken).build();

                new ObjectMapper().writeValue(response.getOutputStream(), token);
//        return token;
    }




}
