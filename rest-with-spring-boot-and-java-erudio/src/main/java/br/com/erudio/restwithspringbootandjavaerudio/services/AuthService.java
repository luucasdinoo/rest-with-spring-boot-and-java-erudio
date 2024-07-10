package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.repositories.UserRepository;
import br.com.erudio.restwithspringbootandjavaerudio.security.dto.AccountCredentialDto;
import br.com.erudio.restwithspringbootandjavaerudio.security.dto.TokenDto;
import br.com.erudio.restwithspringbootandjavaerudio.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public ResponseEntity<TokenDto> signin(AccountCredentialDto data){
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = userRepository.findByUsername(username);

            var tokenResponse = new TokenDto();

            if (user != null)
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            else
                throw new UsernameNotFoundException("Username not found");

            return ResponseEntity.ok(tokenResponse);
        }
        catch (Exception e) {
            throw  new BadCredentialsException("Invalid username or password");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken){
            var user = userRepository.findByUsername(username);

            var tokenResponse = new TokenDto();

            if (user != null)
                tokenResponse = tokenProvider.refreshToken(refreshToken);
            else
                throw new UsernameNotFoundException("Username not found");

            return ResponseEntity.ok(tokenResponse);
    }
}
