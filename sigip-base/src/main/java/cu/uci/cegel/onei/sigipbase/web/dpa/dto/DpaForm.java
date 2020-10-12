package cu.uci.cegel.onei.sigipbase.web.dpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DpaForm {
    private String descripcion;
    private String codigodpa;
    private Boolean activo;
    private Long idpadre;
}
