package cu.uci.cegel.onei.sigipipc.resolvers.indice;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.OneiIndex;
import cu.uci.cegel.onei.sigipipc.services.OneiIndexService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "IndiceResolver")
public class IndiceResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private OneiIndexService oneiIndexService;

    // Queries
    public List<OneiIndex> oneiIndexes() {
        return oneiIndexService.getAll();
    }

    // Mutations
    public OneiIndex addOneiIndex(String description) {
        return oneiIndexService.addOneiIndex(description);
    }

}
