package cu.uci.cegel.onei.sigipbase.web.permiso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermisoForm {

    private String permiso;
    private String description;
    private String activo;

    public boolean isActivo() {
        return (activo != null) && activo.toUpperCase().equalsIgnoreCase("true".toUpperCase());
    }

}
