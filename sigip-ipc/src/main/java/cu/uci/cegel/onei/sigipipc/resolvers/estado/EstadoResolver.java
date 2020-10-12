package cu.uci.cegel.onei.sigipipc.resolvers.estado;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.State;
import cu.uci.cegel.onei.sigipipc.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "EstadoResolver")
public class EstadoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private StateService stateService;

    // Queries
    public List<State> allStates() {
        return stateService.allStates();
    }

    public State stateByDescripcion(String descrip) {
        return stateService.stateByDescripcion(descrip);
    }

    public State stateById(long ident) {
        return stateService.stateById(ident);
    }

    // Mutations
    public State addState(State estado) {
        return stateService.addState(estado);
    }

    public State editState(State estado) {
        return stateService.editState(estado);
    }
}
