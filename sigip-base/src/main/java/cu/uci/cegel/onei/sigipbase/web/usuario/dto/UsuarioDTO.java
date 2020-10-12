package cu.uci.cegel.onei.sigipbase.web.usuario.dto;

import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilFecha;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Data
public class UsuarioDTO {

    private String primernombre;
    private String segundonombre;
    private String primerapellido;
    private String segundoapellido;
    private Boolean activo;
    private String fechainicio;
    private String fechafin;
    private String carnetidentidad;
    private String email;
    private String username;
    private String password;
    private String confirmpassword;
    private Long dpa;
    private Long cargo;
    private List<Long> permisos;
    private List<Long> roles;

    public LocalDate obtenerFechaInicio() {
        return (fechainicio != null) ? UtilFecha.convertir(this.fechainicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : LocalDate.now();
    }

    public LocalDate obtenerFechaFin() {
        return (fechafin != null) ? UtilFecha.convertir(this.fechafin, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }

}

