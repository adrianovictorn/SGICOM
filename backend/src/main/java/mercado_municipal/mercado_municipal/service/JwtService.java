package mercado_municipal.mercado_municipal.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.entity.Usuario;
import mercado_municipal.mercado_municipal.repository.UsuarioRepository;



@Service
public class JwtService {

    private final UsuarioRepository usuarioRepository;

    public JwtService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationInMs;

    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    MacAlgorithm algorithm = Jwts.SIG.HS256;

    public String generateToken(UserDetails userDetails){
        long now = System.currentTimeMillis();
        var roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList();

        String cpf = userDetails.getUsername();

        Usuario usuario = usuarioRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado !"));

        String nome = usuario.getNome();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("nome", nome)
                .claim("roles", roles)
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtExpirationInMs))
                .signWith(getSignInKey(), algorithm)
                .compact();
    }


    public String extractUsername(String token){
        String jwt = stripBearer(token);
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload(); 

        return claims.getSubject();
    }

    private String stripBearer(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    public Boolean isValidToken(String token, UserDetails userDetails){
        try{

            var claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(stripBearer(token))
                .getPayload();

            String username = claims.getSubject();
            var notExpiration = claims.getExpiration().after(new Date());

            return (username.equals(userDetails.getUsername()) && notExpiration);
        }catch(io.jsonwebtoken.JwtException e){
            return false;
        }
    }
    
}
