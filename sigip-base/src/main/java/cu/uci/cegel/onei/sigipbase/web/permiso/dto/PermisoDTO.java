package cu.uci.cegel.onei.sigipbase.web.permiso.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PermisoDTO {

    private String permiso;
    private String description;
    private Boolean activo;
    private Long idpadre;
    private Long rol;
}
