package br.com.vrfortaleza.integracaoapi.controller.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.services.interfaces.exportacao.fortes.Fortes175Service;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;

public class ExportacaoFortesController {
    private final Fortes175Service oFortes175Service;

    public ExportacaoFortesController() {
        this.oFortes175Service = new Fortes175Service();
    }

    public ExportacaoFortesController(Fortes175Service oFortes175Service) {
        this.oFortes175Service = oFortes175Service;
    }

    public void exportar(ExportarFortesVO exportacao) throws Exception {
        this.oFortes175Service.exportar(exportacao, exportacao.idLoja);
    }
}
