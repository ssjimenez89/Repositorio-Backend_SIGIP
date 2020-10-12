package cu.uci.cegel.onei.sigipbase.web.traza.dto;

import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilFecha;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@Data
public class TrazaForm {

    String id;
    String fechaI;
    String fechaF;
    String usuario;
    String ip;
    String descripcion;
    String tipoOperacion;
    String tipoDeTraza;
    String codEntidad;
    String dirEntidad;
    String desEntidad;


    public LocalDate obtenerFechaI() {
//        return (fechaI != null) ? UtilFecha.convertir(this.fechaI, DateTimeFormatter.ISO_DATE_TIME) : null;
        return (fechaI != null) ? UtilFecha.convertir(this.fechaI, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    public LocalDate obtenerFechaF() {
//        return (fechaF != null) ? UtilFecha.convertir(this.fechaF, DateTimeFormatter.ISO_DATE_TIME) : null;
        return (fechaF != null) ? UtilFecha.convertir(this.fechaF.concat(" 23:59"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
    public LocalDateTime obtenerFechaFinNew() {
        return (fechaF != null) ? UtilFecha.convertirToLocalDateTime(this.fechaF.concat(" 23:59"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}
