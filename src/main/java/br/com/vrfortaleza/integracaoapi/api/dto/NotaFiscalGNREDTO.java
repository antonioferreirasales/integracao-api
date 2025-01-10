package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotaFiscalGNREDTO {
    private Integer modelo;
    private LocalDateTime mesReferencia;
}
