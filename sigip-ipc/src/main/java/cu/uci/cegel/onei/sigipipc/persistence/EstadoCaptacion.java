package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Getter;

@Getter
public enum EstadoCaptacion {
    CAPTADO("Captado", 4L),
    PENDIENTE("Pendiente", 5L),
    INICIAL("Inicial", 6L);


    String estado;
    Long id;

    EstadoCaptacion(String estado, Long id) {
        this.estado = estado;
        this.id = id;
    }
}
