package cu.uci.cegel.onei.sigipbase.infrastructure.traza.notifier;

import cu.uci.cegel.base.integration.messaging.events.SystemEvent;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TipoOperacion;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TiposTraza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class Notifier {

    @Autowired
    KafkaTemplate<String, Object> template;

    public void publish(TiposTraza tipoTraza, String descripcion, TipoOperacion tipoOperacion) {
        template.send("sigip", SystemEvent.of(tipoTraza.getDescripcion(), descripcion, getCurrentUser(), getRequestRemoteAddr(), tipoOperacion.getDescripcion()));
    }

    private static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    private static String getRequestRemoteAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getRemoteAddr();
    }

    public String getUser(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
