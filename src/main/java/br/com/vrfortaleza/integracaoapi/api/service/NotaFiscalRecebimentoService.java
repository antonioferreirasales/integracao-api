package br.com.vrfortaleza.integracaoapi.api.service;

import br.com.vrfortaleza.integracaoapi.api.APIClient;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.records.NotaRecebimentoResponse;
import br.com.vrfortaleza.integracaoapi.vo.TipoData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import br.com.vrfortaleza.integracaoapi.api.util.HtppGetUtil;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static br.com.vrfortaleza.integracaoapi.api.URL.NOTA_RECEBIMENTO_URL;

public class NotaFiscalRecebimentoService {
    public List<NotaFiscalRecebimentoDTO> getNotaFiscalRecebimento(String acess_token, LocalDate dataInicio, LocalDate dataFinal, int tipoData) throws IOException, InterruptedException {
        List<NotaFiscalRecebimentoDTO> allRecords = new ArrayList<>();
        final int PAGE_LIMIT = 500;
        int currentOffset = 0;
        boolean hasMoreRecords = false;
        String tipoDataParam = tipoData == TipoData.ENTRADA.getId() ? "periodoEntrada" : "periodoEmissao";
        String filter = String.format("{\"%s\":{\"_datePipe\":{\"locale\":\"pt-BR\"},\"start\":\"%s\",\"end\":\"%s\"}}", tipoDataParam, dataInicio.toString(), dataFinal.toString());
        String limit = String.format("?limit=%d", PAGE_LIMIT);
        String offset = String.format("offset=%d", currentOffset);
        String encodedFilter = URLEncoder.encode(filter, StandardCharsets.UTF_8);
        String URL_PERIODO = NOTA_RECEBIMENTO_URL + limit + "&" + offset + "&" + "filter=" + encodedFilter;;
        HttpClient client = APIClient.getClient();
        HttpRequest request = HtppGetUtil.createRequest(URL_PERIODO, acess_token);
        do {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                NotaRecebimentoResponse notaRecebimentoResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.body(), NotaRecebimentoResponse.class);
                allRecords.addAll(notaRecebimentoResponse.data());
                int currentNumberOfRecords = notaRecebimentoResponse.data().size();
                hasMoreRecords = currentNumberOfRecords == PAGE_LIMIT;
                currentOffset++;
            } else {
                throw new RuntimeException("Falha ao buscar dados da API: " + response.statusCode() + " | " + response.body());
            }

        } while (hasMoreRecords);

        return allRecords;
    }
}


