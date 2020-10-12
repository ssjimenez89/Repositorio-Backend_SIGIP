package cu.uci.cegel.onei.sigipipc.resolvers.incidencia;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Incidence;
import cu.uci.cegel.onei.sigipipc.services.IncidenceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "IncidenciaResolver")
public class IncidenciaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private IncidenceService incidenceService;

    // Queries
    public List<Incidence> incidences() {
        return incidenceService.findAll();
    }

    public Incidence incidenceByAcronym(String acronym) {
        return incidenceService.incidenceByAcronym(acronym);
    }

    public List<Incidence> incidencePaging(int page, int size) {
        return incidenceService.findAllPaging(page, size);
    }

    public int totalIncidence() {
        return incidenceService.totalIncidence();
    }

    public Incidence incidenceById(Long id) {
        return incidenceService.incidenceById(id);
    }

    public List<Incidence> incidenceByDescriptionContains(String description, int page, int size) {
        return incidenceService.findByDescriptionContains(description, page, size);
    }

    public int totalIncidenceByDescriptionContains(String desciption) {
        return incidenceService.totalByDescriptionContains(desciption);
    }

    // Mutations
    public Incidence addIncidence(String description, String acronym, Boolean active) {
        return incidenceService.add(description, acronym, active);
    }

    public Incidence editIncidence(Long id, String description, String acronym, Boolean active) {
        return incidenceService.edit(id, description, acronym, active);
    }

}
