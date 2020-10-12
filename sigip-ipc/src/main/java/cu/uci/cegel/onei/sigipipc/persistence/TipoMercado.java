package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Getter;

@Getter
public enum TipoMercado {

    ESTATAL("Estatal", 1),
    NO_ESTATAL("No Estatal", 2),
    AGROPECUARIO("Agropecuario", 3);

    String descripcion;
    int tipo;

    TipoMercado(String descripcion, int tipo) {
        this.descripcion = descripcion;
        this.tipo = tipo;
    }
}
