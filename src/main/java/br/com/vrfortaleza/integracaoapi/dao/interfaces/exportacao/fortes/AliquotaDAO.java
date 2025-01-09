package br.com.vrfortaleza.integracaoapi.dao.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.vo.SituacaoTributaria;

public class AliquotaDAO {
    public boolean isIsento(int i_situacaoTributaria) throws Exception {
        return i_situacaoTributaria == SituacaoTributaria.ISENTO.getId() || i_situacaoTributaria == SituacaoTributaria.NAO_TRIBUTADO.getId() || i_situacaoTributaria == SituacaoTributaria.SUSPENSAO.getId();
    }

    public boolean isOutras(int i_situacaoTributaria) throws Exception {
        return i_situacaoTributaria == SituacaoTributaria.OUTRAS.getId() || i_situacaoTributaria == SituacaoTributaria.DIFERIMENTO.getId();
    }
}
