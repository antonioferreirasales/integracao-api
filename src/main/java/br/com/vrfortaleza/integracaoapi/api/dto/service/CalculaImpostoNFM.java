package br.com.vrfortaleza.integracaoapi.api.dto.service;

import br.com.vrfortaleza.integracaoapi.api.dto.EmitenteDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.ProdutoDTO;
import br.com.vrfortaleza.integracaoapi.vo.TipoEstado;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculaImpostoNFM {
    public List<ImpostoNFMDTO> calculaImpostoNFM(List<ProdutoDTO> produtos, EmitenteDTO emitente) {
        Map<String, List<ProdutoDTO>> agrupados = produtos.stream()
                .collect(Collectors.groupingBy(item -> item.getCFOP() + "-" + emitente.getUf() + "-" + item.getCSTICMS() + "-" + item.getCstpiscofins() + "-"
                        + item.getICMS() + "-" + item.getFCP() + "-" + item.getOrigemMercadoria()));

        return agrupados.entrySet().stream()
                .map(entry -> {
                    List<ProdutoDTO> grupo = entry.getValue();
                    double valorTotalOperacao = grupo.stream()
                            .mapToDouble(item -> item.getValorBaseCalculoICMS()
                                    + item.getValorIsento()
                                    + item.getValorFrete()
                                    + item.getValorOutrasDespesas()
                                    - item.getValorDesconto()
                                    + item.getValorICMSST())
                            .sum();
                    double baseCalculoICMS = grupo.stream().mapToDouble(ProdutoDTO::getValorBaseCalculoICMS).sum();
                    double valorICMS = grupo.stream().mapToDouble(ProdutoDTO::getValorICMS).sum();
                    double valorIsento = grupo.stream().mapToDouble(ProdutoDTO::getValorIsento).sum();
                    double valorOutras = grupo.stream().mapToDouble(ProdutoDTO::getValorOutrasDespesas).sum();
                    double valorIPI = grupo.stream().mapToDouble(ProdutoDTO::getValorIPI).sum();
                    double valorFCP = grupo.stream().mapToDouble(ProdutoDTO::getValorFCP).sum();
                    double baseCalculoICMSST = grupo.stream().mapToDouble(ProdutoDTO::getValorBaseCalculoICMSST).sum();
                    double valorFCPST = grupo.stream().mapToDouble(ProdutoDTO::getValorFCPST).sum();

                    double aliquotaICMS = grupo.get(0).getICMS();
                    double FCP = grupo.get(0).getFCP();
                    String CFOP = grupo.get(0).getCFOP();
                    Integer CST = grupo.get(0).getCSTICMS();
                    Integer CSTPisCofins = grupo.get(0).getCstpiscofins();
                    Integer tipoOrigem = grupo.get(0).getOrigemMercadoria();

                    return new ImpostoNFMDTO(
                            baseCalculoICMS,
                            baseCalculoICMSST,
                            valorTotalOperacao,
                            aliquotaICMS,
                            valorICMS,
                            valorIsento,
                            valorOutras,
                            valorIPI,
                            FCP,
                            valorFCP,
                            valorFCPST,
                            CFOP,
                            CST,
                            CSTPisCofins,
                            emitente.getSiglaUF(),
                            tipoOrigem
                    );
                })
                .collect(Collectors.toList());
    }

}
