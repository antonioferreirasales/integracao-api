package br.com.vrfortaleza.integracaoapi.api.dto.service;

import br.com.vrfortaleza.integracaoapi.vo.SituacaoOperacaoSimplesNacional;
import br.com.vrfortaleza.integracaoapi.vo.SituacaoTributaria;

import java.util.ArrayList;
import java.util.List;

public class SituacaoTributariaFortesService {
    public List<Integer> get() {
        List<Integer> vCstPermitido = new ArrayList<>();
        vCstPermitido.add(SituacaoTributaria.TRIBUTADO_ICMS_ST.getId());
        vCstPermitido.add(SituacaoTributaria.ISENTO_ICMS_ST.getId());
        vCstPermitido.add(SituacaoTributaria.SUBSTITUIDO.getId());
        vCstPermitido.add(SituacaoTributaria.REDUCAO_BASE_CALCULO_ICMS_ST.getId());
        vCstPermitido.add(SituacaoTributaria.OUTRAS.getId());
        vCstPermitido.add(SituacaoOperacaoSimplesNacional.TRIBUTADA_COM_CREDITO_ICMS_ST.getId());
        vCstPermitido.add(SituacaoOperacaoSimplesNacional.TRIBUTADA_SEM_CREDITO_ICMS_ST.getId());
        vCstPermitido.add(SituacaoOperacaoSimplesNacional.ISENCAO_ICMS_ST.getId());
        vCstPermitido.add(SituacaoOperacaoSimplesNacional.COBRADO_ANTERIORMENTE.getId());
        vCstPermitido.add(SituacaoOperacaoSimplesNacional.OUTRAS.getId());
        return vCstPermitido;
    }
}
