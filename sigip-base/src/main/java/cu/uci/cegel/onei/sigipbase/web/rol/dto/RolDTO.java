package cu.uci.cegel.onei.sigipbase.web.rol.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RolDTO {

    private String rol;
    private String description;
    private Boolean activo;
    private List<Long> permisos;
}
