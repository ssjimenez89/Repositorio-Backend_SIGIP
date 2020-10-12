package cu.uci.cegel.onei.sigipipc.resolvers.nomenclador;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.Nomenclator;
import cu.uci.cegel.onei.sigipipc.services.NomenclatorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "NomencladorResolver")
public class NomencladorResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private NomenclatorService nomenclatorService;

    // Queries
    public List<Nomenclator> nomenclatorsPaging(int page, int size) {
        return nomenclatorService.findAllPaging(page, size);
    }
    public Nomenclator nomenclatorById(Long id){
        return nomenclatorService.nomenclatorById(id);
    }
    public int totalAllNomenclators() {
        return nomenclatorService.totalNomenclators();
    }

    public List<Nomenclator> nomenclatorsByDescriptionContains(String description, int page, int size) {
        return nomenclatorService.findByDescriptionContains(description, page, size);
    }

    public int totalAllNomenclatorsByDescriptionContains(String desciption) {
        return nomenclatorService.totalByDescriptionContains(desciption);
    }

    // Mutations

}