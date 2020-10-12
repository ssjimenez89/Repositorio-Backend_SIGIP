package cu.uci.cegel.onei.sigipbase.web.dpa.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DpaDTO {
    private Long id;
    private String descripcion;
    private String codigodpa;
    private Boolean activo;
    private Long idpadre;
}

