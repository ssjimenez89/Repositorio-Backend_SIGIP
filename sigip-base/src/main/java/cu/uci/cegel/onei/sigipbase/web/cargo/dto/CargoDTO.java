package cu.uci.cegel.onei.sigipbase.web.cargo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CargoDTO {
    private Long id;
    private String descripcion;
    private Boolean activo;
}

