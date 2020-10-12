package cu.uci.cegel.onei.sigipbase.web.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioForm {
    //trabajador
    private String primernombre;
    private String primerapellido;
    private String segundoapellido;
    //Usuario
    private String username;
    private Long cargo;
    private Long dpa;
    private Long rolbusqueda;
    private Long activo_busqueda;
}
