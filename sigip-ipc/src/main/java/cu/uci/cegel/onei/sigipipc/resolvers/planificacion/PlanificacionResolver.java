package cu.uci.cegel.onei.sigipipc.resolvers.planificacion;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.PlanningInput;
import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.services.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "PlanificacionResolver")
public class PlanificacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private PlanningService planningService;

    //Queries
    public List<Planning> plannings() {
        return planningService.findAll();
    }

    public List<Planning> planningsPaging(int paging, int size) {
        return planningService.findAllPaging(paging, size);
    }

    public Planning planningById(long id) {
        return planningService.planningById(id);
    }

    public int totalPlannings() {
        return planningService.totalPlannings();
    }

    public List<Planning> planningsDayXWeek(int week1, int week2, int week3, int week4) {
        return planningService.planningsDayXWeek(week1, week2, week3, week4);
    }

    //Mutations
    public Planning addPlanning(PlanningInput planningInput) {
        return planningService.addPlaning(planningInput);
    }

    public Planning editPlanning(PlanningInput planningInput) {
        return planningService.editPlaning(planningInput);
    }
}
