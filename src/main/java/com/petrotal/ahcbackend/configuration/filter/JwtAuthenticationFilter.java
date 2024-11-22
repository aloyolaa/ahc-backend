package com.petrotal.ahcbackend.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotal.ahcbackend.dto.ErrorResponse;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.entity.AccessHistory;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.exception.UserAuthenticationException;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.petrotal.ahcbackend.configuration.TokenJwtConfig.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AccessHistoryService accessHistoryService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new UserAuthenticationException("Error al leer los datos del usuario de la solicitud.", e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        User user = userService.findByUsername(principal.getUsername());

        Claims claims = Jwts.claims()
                .add("firstName", user.getFirstName())
                .add("lastName", user.getLastName())
                .add("authorities", new ObjectMapper().writeValueAsString(principal.getAuthorities()))
                .build();
        String token = Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(SECRET_KEY)
                .compact();
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", "Has iniciado sesión con éxito.");
        ResponseDto responseDto = new ResponseDto(body, true);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);

        accessHistoryService.save(new AccessHistory(user, LocalDateTime.now()));
        log.info("El usuario {} ha iniciado sesión.", user.getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseDto errorResponse = null;

        if (failed instanceof BadCredentialsException) {
            errorResponse = generateErrorResponse("Username o Password incorrectos.");
        }

        if (failed instanceof DisabledException) {
            errorResponse = generateErrorResponse("No tiene acceso al sistema.");
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }

    private ResponseDto generateErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse("Error en la autenticación", message);
        return new ResponseDto(errorResponse, false);
    }
}
