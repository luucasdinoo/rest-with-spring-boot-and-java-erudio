package br.com.erudio.restwithspringbootandjavaerudio.controllers;

import br.com.erudio.restwithspringbootandjavaerudio.security.dto.AccountCredentialDto;
import br.com.erudio.restwithspringbootandjavaerudio.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialDto data) {
        if (checkParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = authService.signin(data);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String refreshToken) {
        if (checkParamsIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = authService.refreshToken(username, refreshToken);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    private boolean checkParamsIsNotNull(AccountCredentialDto data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() ||
                data.getPassword() == null || data.getPassword().isBlank();
    }

    private boolean checkParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }
}
