package cu.uci.cegel.onei.sigipbase.domain.trazas;

import cu.uci.cegel.base.integration.messaging.events.SystemEvent;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilFecha;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class TrazaFactory {

    public Traza convertir(String descripcion, LocalDate fecha, String usuario, String ip, String tipoOperacion, String tipoTrazas, String codEntidad) {
        return Traza.builder()
                .descripcion(descripcion)
                .fecha(fecha.toString())
                .usuario(usuario)
                .ip(ip)
                .tipoOperacion(tipoOperacion)
                .tipoDeTraza(tipoTrazas)
                .build();
    }

    public Traza createForm(SystemEvent systemEvent) {

        return Traza.builder()
                .descripcion(systemEvent.getDescription())
                .usuario(systemEvent.getUsuario())
                .fecha(UtilFecha.convertirLocalDateTimeHora(LocalDateTime.now(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .ip(systemEvent.getIp())
                .tipoOperacion(systemEvent.getTipoOperacion())
                .tipoDeTraza(systemEvent.getTipoTrazas())
                .build();
    }
}
