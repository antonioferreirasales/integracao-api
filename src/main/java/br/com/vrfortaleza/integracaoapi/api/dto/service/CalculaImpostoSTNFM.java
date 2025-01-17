package br.com.vrfortaleza.integracaoapi.api.dto.service;

import br.com.vrfortaleza.integracaoapi.api.dto.EmitenteDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.ProdutoDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculaImpostoSTNFM {
    public List<ImpostoSTNFMDTO> calculaImpostoSTNFM(List<ProdutoDTO> produtos, EmitenteDTO emitente) {
        Map<String, List<ProdutoDTO>> agrupados = produtos.stream()
                .collect(Collectors.groupingBy(item -> item.getICMS() + "-" + item.getFCP()));

        return agrupados.entrySet().stream()
                .map(entry -> {
                    List<ProdutoDTO> grupo = entry.getValue();
                    double valorTotal = grupo.stream().mapToDouble(ProdutoDTO::getValorTotalBruto).sum();
                    double FCP = grupo.get(0).getFCP();
                    Integer CST = grupo.get(0).getCSTICMS();

                    return new ImpostoSTNFMDTO(
                            valorTotal,
                            CST,
                            FCP
                    );
                })
                .collect(Collectors.toList());
    }

}
