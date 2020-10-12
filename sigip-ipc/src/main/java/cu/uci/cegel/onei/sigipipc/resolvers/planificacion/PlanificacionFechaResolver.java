package cu.uci.cegel.onei.sigipipc.resolvers.planificacion;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.persistence.PlanningDate;
import cu.uci.cegel.onei.sigipipc.services.PlanningDateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Resolver(name = "PlanificacionFechaResolver")
public class PlanificacionFechaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private PlanningDateService planningDateService;

    //Queries
    public List<PlanningDate> obtenerTodasPlanificaciones() {
        return planningDateService.findAll();
    }

    public PlanningDate obtenerPlanificacionById(long id) {
        return planningDateService.obtenerPlanificacionById(id);
    }

    public List<Integer> obtenerAnnosPlanificados() {
        return planningDateService.obtenerAnnosPlanificados();
    }

    public List<Integer> generarAnnos(int cantidadAnnosFuturos) {
        return planningDateService.generarAnnos(cantidadAnnosFuturos);
    }

    List<Planning> diasPlanificados(LocalDate desde, LocalDate hasta) {
        return planningDateService.diasPlanificados(desde, hasta);
    }

    //Mutations
    public int generarPlanificacionFecha(int anno) {
        return planningDateService.generarPlanificacionFecha(anno);
    }
}
