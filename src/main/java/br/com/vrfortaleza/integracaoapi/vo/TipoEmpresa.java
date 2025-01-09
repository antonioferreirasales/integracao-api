package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
public enum TipoEmpresa {
    LUCRO_PRESUMIDO(1),
    PRODUTOR_RURAL_PESSOA_JURIDICA(2),
    LUCRO_REAL(3),
    LUCRO_REAL_ESTIMADO(4),
    LUCRO_REAL_ESTIMADO_OP(5),
    SOCIEDADE_CIVIL(6),
    PESSOA_FISICA(7),
    ME_SIMPLES(8),
    EPP_SIMPLES(9),
    MEI(10),
    EIRELI(11),
    PRODUTOR_RURAL_PESSOA_FISICA(12),
    SIMPLES_NACIONAL_EXCESSO_SUBLIMITE_RECEITA_BRUTA(13);

    private int id = 0;

    TipoEmpresa(int i_id) {
        this.id = i_id;
    }

    public static TipoEmpresa getById(int pIdTipoEmpresa) {
        return Arrays.<TipoEmpresa>asList(values())
                .stream()
                .filter(pTipoEmpresa -> (pTipoEmpresa.getId() == pIdTipoEmpresa))
                .findFirst()
                .get();
    }

    public static Stream<TipoEmpresa> stream() {
        return Stream.of(values());
    }
}
