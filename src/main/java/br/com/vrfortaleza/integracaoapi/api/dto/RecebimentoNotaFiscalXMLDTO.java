package br.com.vrfortaleza.integracaoapi.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecebimentoNotaFiscalXMLDTO {
    private Integer id;
    private String chaveNfe;
    private String nomeArquivo;
    private Integer numeroNota;
    private Integer idEmitente;
    private String cpfCnpjEmitente;
    private String nomeRazaoSocialEmitente;
    private Boolean emitenteNaoCadastrado;
    private Integer idDestinatario;
    private String cnpjDestinatario;
    private String razaoSocialDestinatario;
    private LocalDateTime dataHoraEmissao;
    private String naturezaOperacao;
    private String justificativa;
    private String xmlRetorno;
    private String manifestacao;
    private Boolean carregada;
    private Integer idTipoMovimentacao;
    private Boolean conferidoLancamento;
}
