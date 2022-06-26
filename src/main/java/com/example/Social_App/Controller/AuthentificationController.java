package com.example.Social_App.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Social_App.Model.Role;
import com.example.Social_App.Model.User;
import com.example.Social_App.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
public class AuthentificationController {

    private final UserService userService;


    public AuthentificationController(final UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/api/refreshToken")
    public void refreshToken(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                final String refreshToken = authorizationHeader.substring("Bearer ".length());
                final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                final JWTVerifier verifier = JWT.require(algorithm).build();
                final DecodedJWT decodedJWT = verifier.verify(refreshToken);
                final String username = decodedJWT.getSubject();
                final User user = userService.findByUsername(username);
                final String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                final Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (final Exception exception) {
                log.error("error logging in : {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                final Map<String, String> error_message = new HashMap<>();
                error_message.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error_message);

            }
        } else {
            throw new RuntimeException("Refresh Token is missing");

        }


    }

}

   
