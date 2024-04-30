package com.practica.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    //Para lectura de llaves y usuario
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    //Metodo de generar el token
    //Para extraer user y autorizaciones
    public String createToken(Authentication authentication) {

        //Definir algoritmo de incriptacion
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        //Extraer user
        String username = authentication.getPrincipal().toString();

        //Obtengo las autorizaciones (READ, CREATE, DELETE, ETC...)
        String authorites = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); //Separa con comas

        //Genera token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorites)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return jwtToken;

    }

    //Decodificacion del token
    public DecodedJWT validateToke(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT;

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not authorized...");
        }

    }

    //Extraer usuario
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    //Obtener claim especifico
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    //Obtener todos los claims
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
