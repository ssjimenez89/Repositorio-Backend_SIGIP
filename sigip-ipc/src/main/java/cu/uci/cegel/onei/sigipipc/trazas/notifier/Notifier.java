package cu.uci.cegel.onei.sigipipc.trazas.notifier;

import cu.uci.cegel.base.integration.messaging.events.SystemEvent;
import cu.uci.cegel.onei.sigipipc.trazas.TipoOperacion;
import cu.uci.cegel.onei.sigipipc.trazas.TiposTraza;
import graphql.servlet.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class Notifier {

    @Autowired
    KafkaTemplate<String, Object> template;
    @Autowired
    HttpServletRequest request;

    public void publish(TiposTraza tipoTraza, String descripcion, TipoOperacion tipoOperacion) {
        template.send("sigip", SystemEvent.of(tipoTraza.getDescripcion(), descripcion, getCurrentUser(), getRequestRemoteAddr(), tipoOperacion.getDescripcion()));
    }

    private static String getCurrentUser() {
        String salida = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return salida;
    }

    private String getRequestRemoteAddr() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
//        return request.getRemoteAddr();

        return this.request.getRemoteAddr();
    }

    private String getRemoteAddress1() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof NativeWebRequest) {
            HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
            return request.getRemoteAddr();
        }
        return null;
    }

    public String getUser(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
