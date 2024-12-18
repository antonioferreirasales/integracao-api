package br.com.vrfortaleza.integracaoapi.api.service;

import br.com.vrfortaleza.integracaoapi.api.APIClient;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDetalhesDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.records.NotaRecebimentoDetalhadoResponse;
import br.com.vrfortaleza.integracaoapi.api.util.HtppGetUtil;
import br.com.vrfortaleza.integracaoapi.config.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static br.com.vrfortaleza.integracaoapi.api.URL.NOTA_RECEBIMENTO_URL;

public class NotaFiscalRecebimentoDetalhesService {
    private static final int MAX_RETRIES = 5;

    public NotaFiscalRecebimentoDetalhesDTO getNotaFiscalRecebimentoDetalhes(String token, int idNotaFiscal) {
        String NOTA_RECEBIMENTO_DETALHES_URL = NOTA_RECEBIMENTO_URL + "/" + idNotaFiscal;
        HttpClient client = APIClient.getClient();
        HttpRequest request = HtppGetUtil.createRequest(NOTA_RECEBIMENTO_DETALHES_URL, token);

        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    NotaRecebimentoDetalhadoResponse notaRecebimentoResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.body(), NotaRecebimentoDetalhadoResponse.class);
                    return notaRecebimentoResponse.data();
                } else if (response.statusCode() == 404) {
                    return new NotaFiscalRecebimentoDetalhesDTO();
                } else {
                    throw new RuntimeException("Falha ao buscar dados da API da Nota " + idNotaFiscal + ": Código | " + response.statusCode() + " " + response.body());
                }
            } catch (Exception e) {
                Log.error(this.getClass(), "Erro ao buscar dados da API: " + e.getMessage());
                System.out.println("Erro ao buscar dados da API: " + e.getMessage());
            }
            try {
                TimeUnit.SECONDS.sleep((long) Math.pow(2, i));
            } catch (InterruptedException e) {
                Log.error(this.getClass(),"Erro ao dormir thread: " + e.getMessage());
            }
        }
        throw new RuntimeException("Falha após " + MAX_RETRIES + " tentativas para buscar dados da API da Nota " + idNotaFiscal);
    }
}
