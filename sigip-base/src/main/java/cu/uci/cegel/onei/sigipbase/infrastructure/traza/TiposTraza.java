package cu.uci.cegel.onei.sigipbase.infrastructure.traza;

import lombok.Getter;

@Getter
public enum TiposTraza {

    FUNCIONAL("Funcional"),
    TECNICA("Técnica");

    String descripcion;
    private TiposTraza(String descripcion) {
        this.descripcion=descripcion;
    }
}
