package br.com.vrfortaleza.integracaoapi.dao.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.vo.SituacaoTributaria;
import br.com.vrfortaleza.integracaoapi.vo.TipoEstado;

public class AliquotaDAO {
    public boolean isIsento(int i_situacaoTributaria) throws Exception {
        return i_situacaoTributaria == SituacaoTributaria.ISENTO.getId() || i_situacaoTributaria == SituacaoTributaria.NAO_TRIBUTADO.getId() || i_situacaoTributaria == SituacaoTributaria.SUSPENSAO.getId();
    }

    public boolean isOutras(int i_situacaoTributaria) throws Exception {
        return i_situacaoTributaria == SituacaoTributaria.OUTRAS.getId() || i_situacaoTributaria == SituacaoTributaria.DIFERIMENTO.getId();
    }
    public boolean isSubstituido(int i_situacaoTributaria) throws Exception {
        int id_estado = 23;
        return i_situacaoTributaria == SituacaoTributaria.SUBSTITUIDO.getId() || i_situacaoTributaria == SituacaoTributaria.REDUCAO_BASE_CALCULO_ICMS_ST
                .getId() || i_situacaoTributaria == SituacaoTributaria.TRIBUTADO_ICMS_ST
                .getId() || (i_situacaoTributaria == SituacaoTributaria.ISENTO_ICMS_ST
                .getId() && id_estado != TipoEstado.CE.getId());
    }

    public boolean isSubstituido(int i_situacaoTributaria, int id_estado) throws Exception {
        return i_situacaoTributaria == SituacaoTributaria.SUBSTITUIDO.getId() || i_situacaoTributaria == SituacaoTributaria.REDUCAO_BASE_CALCULO_ICMS_ST
                .getId() || i_situacaoTributaria == SituacaoTributaria.TRIBUTADO_ICMS_ST
                .getId() || (i_situacaoTributaria == SituacaoTributaria.ISENTO_ICMS_ST
                .getId() && id_estado != TipoEstado.CE.getId());
    }
}
