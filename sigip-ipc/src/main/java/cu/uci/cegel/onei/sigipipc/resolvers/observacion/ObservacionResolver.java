package cu.uci.cegel.onei.sigipipc.resolvers.observacion;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Observation;
import cu.uci.cegel.onei.sigipipc.services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "ObservacionResolver")
public class ObservacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private ObservationService observationService;

    // Queries
    public List<Observation> observations() {
        return observationService.findAll();
    }

    public List<Observation> observationByVariedad(Long varId) {
        return observationService.observationByVariedad(varId);
    }

    public Observation observationByDescription(String description) {
        return observationService.observationByDescription(description);
    }

    public Observation observationByAcronym(String acronym) {
        return observationService.observationByAcronym(acronym);
    }

    public List<Observation> observationsPaging(int page, int size) {
        return observationService.findAllPaging(page, size);
    }

    public int totalObservation() {
        return observationService.totalObservation();
    }


    public Observation observationById(Long id) {
        return observationService.observationById(id);
    }

    public List<Observation> observationByDescriptionContains(String description, int page, int size) {
        return observationService.findByDescriptionContains(description, page, size);
    }

    public int totalObservationByDescriptionContains(String desciption) {
        return observationService.totalByDescriptionContains(desciption);
    }

    // Mutations
    public Observation addObservation(String description, String acronym, Boolean active) {
        return observationService.add(description, acronym, active);
    }

    public Observation editObservation(Long id, String description, String acronym, Boolean active) {
        return observationService.edit(id, description, acronym, active);
    }
}
