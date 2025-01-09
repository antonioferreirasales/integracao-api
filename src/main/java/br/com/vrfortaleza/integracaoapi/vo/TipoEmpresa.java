package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
public enum TipoEmpresa {
    LUCRO_REAL(0),
    LUCRO_PRESUMIDO(1),
    SIMPLES_NACIONAL(2),
    MEI(3),
    SIMPLES_NACIONAL_EXCESSO_SUBLIMITE_RECEITA_BRUTA(4),
    LUCRO_REAL_ESTIMADO(5),
    LUCRO_REAL_ESTIMADO_OP(6),
    SOCIEDADE_CIVIL(7),
    PESSOA_FISICA(8),
    ME_SIMPLES(9),
    EPP_SIMPLES(10),
    EIRELI(11),
    PRODUTOR_RURAL_PESSOA_FISICA(12),
    PRODUTOR_RURAL_PESSOA_JURIDICA(13);

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
