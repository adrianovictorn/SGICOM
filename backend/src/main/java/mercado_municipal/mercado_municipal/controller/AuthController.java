package mercado_municipal.mercado_municipal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.auth.AuthResponse;
import mercado_municipal.mercado_municipal.dto.auth.LoginRequest;
import mercado_municipal.mercado_municipal.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    private final JwtService jwtService;
    

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUsuario(@RequestBody LoginRequest login){
        
        var user = new UsernamePasswordAuthenticationToken(login.username(), login.password());
        var authRequest = (User) authenticationManager.authenticate(user).getPrincipal();

        var jwtToken = jwtService.generateToken(authRequest);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}

