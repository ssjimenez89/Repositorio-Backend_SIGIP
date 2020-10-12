package cu.uci.cegel.onei.sigipipc.resolvers.clasificador;

import com.coxautodev.graphql.tools.GraphQLResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Classifier;

@Resolver(name = "ClassifierIndexResolver")
public class ClassifierIndexResolver implements GraphQLResolver<Classifier> {
    public float getIndex(Classifier classifier) {
        return 1.32f;
    }
}
