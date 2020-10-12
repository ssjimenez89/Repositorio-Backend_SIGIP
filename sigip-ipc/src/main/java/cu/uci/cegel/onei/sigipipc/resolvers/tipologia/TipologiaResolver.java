package cu.uci.cegel.onei.sigipipc.resolvers.tipologia;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Typology;
import cu.uci.cegel.onei.sigipipc.services.TypologyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "TipologiaResolver")
public class TipologiaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private TypologyService typologyService;

    //Queries
    public List<Typology> typologies() {
        return typologyService.findAll();
    }

    public List<Typology> typologiesPaging(int page, int size) {
        return typologyService.findAllPaging(page, size);
    }

    public int totalTypology() {
        return typologyService.totalTypology();
    }

    public Typology typologyById(Long id) {
        return typologyService.typologyById(id);
    }

    public List<Typology> typologyByDescriptionContains(String description, int page, int size) {
        return typologyService.findByDescriptionContains(description, page, size);
    }

    public int totalTypologyByDescriptionContains(String desciption) {
        return typologyService.totalByDescriptionContains(desciption);
    }

    //Mutations
    public Typology addTypology(String description, Long categoryId, Boolean active, Boolean imputed) {
        return typologyService.add(description, categoryId, active, imputed);
    }

    public Typology editTypology(Long id, String description, Long categoryId, Boolean active, Boolean imputed) {
        return typologyService.edit(id, description, categoryId, active, imputed);
    }

}
