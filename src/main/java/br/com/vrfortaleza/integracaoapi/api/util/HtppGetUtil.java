package br.com.vrfortaleza.integracaoapi.api.util;

import java.net.URI;
import java.net.http.HttpRequest;

import br.com.vrfortaleza.integracaoapi.config.ApiConfig;

public class HtppGetUtil {
    public static HttpRequest createRequest(String url, String accessToken) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .header("Config-Token", ApiConfig.requireConfigToken())
                .build();
    }
}
