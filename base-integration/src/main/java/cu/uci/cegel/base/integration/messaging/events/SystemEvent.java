package cu.uci.cegel.base.integration.messaging.events;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SystemEvent {
    private String tipoTrazas;
    private String description;
    private String usuario;
    private String ip;
    private String tipoOperacion;

    private SystemEvent(String tipoTrazas, String description, String usuario, String ip, String tipoOperacion) {
        this.tipoTrazas = tipoTrazas;
        this.description = description;
        this.usuario = usuario;
        this.ip = ip;
        this.tipoOperacion = tipoOperacion;
    }

    public static SystemEvent of(String tipoTrazas, String description, String usuario, String ip, String tipoOperacion) {
        return new SystemEvent(tipoTrazas, description, usuario, ip, tipoOperacion);
    }
}
