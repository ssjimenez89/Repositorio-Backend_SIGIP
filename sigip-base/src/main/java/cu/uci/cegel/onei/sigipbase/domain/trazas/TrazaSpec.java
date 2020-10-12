package cu.uci.cegel.onei.sigipbase.domain.trazas;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import cu.uci.cegel.onei.sigipbase.web.traza.dto.TrazaForm;


public class TrazaSpec {


    public static Predicate listar(TrazaForm trazaForm) {
        QTraza user = QTraza.traza;
        BooleanBuilder predicates = new BooleanBuilder();

        if (trazaForm.getUsuario() != null && !trazaForm.getUsuario().isEmpty())
            predicates.and(user.usuario.like("%" + trazaForm.getUsuario() + "%"));
        if ((!trazaForm.getFechaI().isEmpty()) &&(!trazaForm.getFechaF().isEmpty()))
            predicates.and(user.fecha.between(trazaForm.obtenerFechaI().toString(), trazaForm.obtenerFechaFinNew().toString()));
        else if ((!trazaForm.getFechaI().isEmpty()) && (trazaForm.getFechaF().isEmpty()))
            predicates.and(user.fecha.goe(trazaForm.obtenerFechaI().toString()));
        else if ((trazaForm.getFechaI().isEmpty()) && (!trazaForm.getFechaF().isEmpty()))
            predicates.and(user.fecha.loe(trazaForm.obtenerFechaFinNew().toString()));

        if (trazaForm.getTipoOperacion() != null && !trazaForm.getTipoOperacion().isEmpty())
            predicates.and(user.tipoOperacion.like(trazaForm.getTipoOperacion()));


        return predicates;

    }

}
