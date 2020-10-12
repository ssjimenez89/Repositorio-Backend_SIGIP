package cu.uci.cegel.onei.sigipipc.resolvers.ponderacion;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.WeighingType;
import cu.uci.cegel.onei.sigipipc.services.WeighingTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "TipoPonderacionResolver")
public class TipoPonderacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private WeighingTypeService weighingTypeService;

    //Queries
    public List<WeighingType> weighingTypes() {
        return weighingTypeService.findAll();
    }

    public List<WeighingType> weighingTypesPaging(int page, int size) {
        return weighingTypeService.weighingTypesPaging(page, size);
    }

    public WeighingType weighingTypeById(Long id) {
        return weighingTypeService.weighingTypeById(id);
    }

    public int totalWeighingTypes() {
        return weighingTypeService.totalWeighingTypes();
    }

    public List<WeighingType> weighingTypeByDescriptionContains(String description, int page, int size) {
        return weighingTypeService.findByDescriptionContains(description, page, size);
    }

    public int totalWeighingTypesByDescriptionContains(String desciption) {
        return weighingTypeService.totalByDescriptionContains(desciption);
    }

    //Mutations
    public WeighingType addWeighingType(String description, Boolean active) {
        return weighingTypeService.addWeighingType(description, active);
    }

    public WeighingType editWeighingType(Long id, String description, Boolean active) {
        return weighingTypeService.editWeighingType(id, description, active);
    }
}
