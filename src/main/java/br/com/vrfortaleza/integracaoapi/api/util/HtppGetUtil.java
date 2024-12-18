package br.com.vrfortaleza.integracaoapi.api.util;

import java.net.URI;
import java.net.http.HttpRequest;

import static br.com.vrfortaleza.integracaoapi.api.URL.CONFIG_TOKEN;

public class HtppGetUtil {
    public static HttpRequest createRequest(String url, String accessToken) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .header("Config-Token", CONFIG_TOKEN)
                .build();
    }
}
