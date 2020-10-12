package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Getter;

@Getter
public enum TipoObservacion {

    NO_DISPONIBLE_EN_EL_FORMULARIO("ND: No disponible en el formulario", 1L),
    FALTA_ESTACIONAL("FE: Falta estacional", 2L),
    FALTA_DEFINITIVA("FD: Falta definitiva", 3L),
    EN_OFERTA("O: En oferta", 4L),
    FALTA_OCASIONAL("FO: Falta ocasional", 5L),
    PRECIO_NORMAL("PN: Precio Normal", 6L),
    CAMBIO_DE_CANTIDAD_O_UM("UM: Cambio de cantidad o UM", 7L),
    REBAJA("Rebaja", 8L),
    COMPARABLE("Comparable", 9L);

    String descripcion;
    Long tipo;

    TipoObservacion(String descripcion, Long tipo) {
        this.descripcion = descripcion;
        this.tipo = tipo;
    }
}
