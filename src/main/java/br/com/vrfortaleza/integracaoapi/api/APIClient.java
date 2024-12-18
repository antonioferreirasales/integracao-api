package br.com.vrfortaleza.integracaoapi.api;

import lombok.Getter;

import java.net.http.HttpClient;
import java.time.Duration;

public class APIClient {
    @Getter
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

}
