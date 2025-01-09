package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum SituacaoTributariaPisCofins {
    TRIBUTADO(1, "01 - Operação Tributável com Alíquota Básica"),
    TRIBUTADO_ALIQUOTA_DIFERENCIADA(2, "02 - Operação Tributável com Alíquota Diferenciada"),
    TRIBUTADO_UNIDADE_MEDIDA_PRODUTO(3, "03 - Operação Tributável com Alíquota por Unidade de Produto"),
    TRIBUTADO_MONOFASICA(4, "04 - Operação Tributável - Revenda a Alíquota Zero"),
    TRIBUTADO_ST(5, "05 - Operação Tributável - Substituição Tributária"),
    TRIBUTADO_ALIQUOTA_ZERO(6, "06 - Operação Tributável com Alíquota Zero"),
    ISENTO(7, "07 - Operação Isenta da Contribuição"),
    SUSPENSAO_INCIDENCIA(8, "08 - Operação sem Incidência da Contribuição"),
    OUTRAS_ENTRADAS(9, "09 - Outras Operações de Entrada"),
    TRIBUTADO_MERCADO_INTERNO(50, "50 - Crédito Presumido - Aquisição Exclusivamente a Receita Tributada no Mercado Interno"),
    TRIBUTADO_EXCLUSIVA_EXPORTACAO(51, "51 - Crédito Presumido - Aquisição Exclusivamente a Receita de Exportação"),
    TRIBUTADO_INTERNO_EXPORTACAO(52, "52 - Crédito Presumido - Aquisição Vinculada a Receita Tributada no Mercado Interno e de Exportação"),
    NAO_TRIBUTADO(53, "53 - Crédito Presumido - Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
    NAO_TRIBUTADO_EXCLUSIVA_EXPORTACAO(54, "54 - Crédito Presumido - Aquisição Exclusivamente a Receita Não Tributada no Mercado Interno"),
    NAO_TRIBUTADO_INTERNO_EXPORTACAO(55, "55 - Crédito Presumido - Aquisição Vinculada a Receita Não Tributada no Mercado Interno e de Exportação"),
    NAO_TRIBUTADO_INTERNO_EXPORTACAO_PRESUMIDO(56, "56 - Crédito Presumido - Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno e de Exportação"),
    TRIBUTADO_INTERNO_EXPORTACAO_PRESUMIDO(60, "60 - Crédito Presumido - Operação de Aquisição Exclusivamente a Receita Tributada no Mercado Interno"),
    TRIBUTADO_EXCLUSIVA_EXPORTACAO_PRESUMIDO(61, "61 - Crédito Presumido - Operação de Aquisição Exclusivamente a Receita de Exportação"),
    TRIBUTADO_INTERNO_EXPORTACAO_PRESUMIDO2(62, "62 - Crédito Presumido - Operação de Aquisição Vinculada a Receita Tributada no Mercado Interno e de Exportação"),
    TRIBUTADO_NAO_TRIBUTADO_INTERNO_PRESUMIDO(63, "63 - Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
    TRIBUTADO_NAO_TRIBUTADO_INTERNO_EXPORTACAO_PRESUMIDO(64, "64 - Crédito Presumido - Operação de Aquisição Vinculada a Receita Não Tributada no Mercado Interno e de Exportação"),
    TRIBUTADO_NAO_TRIBUTADO_INTERNO_EXPORTACAO(65, "65 - Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
    AQUISICAO_ISENCAO(71, "71 - Operação de Aquisição com Isenção"),
    AQUISICAO_SUSPENSAO(72, "72 - Operação de Aquisição com Suspensão"),
    AQUISICAO_ALIQUOTA_ZERO(73, "73 - Operação de Aquisição com Alíquota Zero"),
    AQUISICAO_SEM_INCIDENCIA(74, "74 - Operação de Aquisição Sem Incidência da Contribuição"),
    OUTRAS_OPERACOES_SAIDA(75, "75 - Outras Operações de Saída"),
    OUTRAS_OPERACOES_ENTRADA(98, "98 - Outras Operações de Entrada"),
    OUTRAS(99, "99 - Outras Operações");

    private Integer id;
    private String descricao;

    SituacaoTributariaPisCofins(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static SituacaoTributariaPisCofins getById(int id) {
        return Stream.of(values())
                .filter(situacaoTributaria -> situacaoTributaria.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Stream<SituacaoTributariaPisCofins> stream() {
        return Stream.of(values());
    }
}


