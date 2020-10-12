package cu.uci.cegel.onei.sigipbase.web.cargo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargoForm {
    private String descripcion;
    private Boolean activo;
}
