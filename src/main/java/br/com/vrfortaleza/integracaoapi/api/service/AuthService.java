package br.com.vrfortaleza.integracaoapi.api.service;

import br.com.vrfortaleza.integracaoapi.api.APIClient;
import br.com.vrfortaleza.integracaoapi.api.dto.records.AuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static br.com.vrfortaleza.integracaoapi.api.URL.AUTH_URL;

public class AuthService {
    public String authenticate(String token) throws IOException, JsonProcessingException, InterruptedException {
        HttpClient cliente = APIClient.getClient();

        Map<String, String> authRequest = new HashMap<>();
        authRequest.put("token", token);

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String authBody = objectMapper.writeValueAsString(authRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUTH_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(authBody))
                .build();
        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            AuthenticationResponse authResponse = objectMapper.readValue(response.body(), AuthenticationResponse.class);
            return authResponse.data().access_token();
        } else {
            throw new RuntimeException("Falha ao autenticar token: " + response.statusCode() + " | " + response.body());
        }
    }
}