package cu.uci.cegel.onei.sigipbase.infrastructure.traza;

import lombok.Getter;

@Getter
public enum TipoOperacion {

    GENERIC_REGISTRAR("Registrar"),
    GENERIC_MODIFICAR ("Modificar"),
    GENERIC_DESACTIVAR ("Desactivar"),
    GENERIC_OBTENER("Obtener");

    String descripcion;

    private TipoOperacion(String descripcion) {
        this.descripcion = descripcion;
    }
}
