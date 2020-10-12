package cu.uci.cegel.onei.sigipipc.resolvers.variedad;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyType;
import cu.uci.cegel.onei.sigipipc.services.VarietyTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "VariedadResolver")
public class VariedadResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    VarietyTypeService varietyTypeService;

    // Queries
    public List<VarietyType> varietyTypes() {
        return varietyTypeService.findAll();
    }

    public List<VarietyType> varietyTypesPaging(int page, int size) {
        return varietyTypeService.varietyTypesPaging(page, size);
    }

    public VarietyType varietyTypeById(Long id) {
        return varietyTypeService.varietyTypeById(id);
    }

    public int totalVarietyTypes() {
        return varietyTypeService.totalVarietyTypes();
    }

    public List<VarietyType> varietyTypeByDescriptionContains(String description, int page, int size) {
        return varietyTypeService.findByDescriptionContains(description, page, size);
    }

    public int totalVarietyTypesByDescriptionContains(String desciption) {
        return varietyTypeService.totalByDescriptionContains(desciption);
    }

    // Mutations
    public VarietyType addVarietyType(String description, Boolean active) {
        return varietyTypeService.addVarietyType(description, active);
    }

    public VarietyType editVarietyType(Long id, String description, Boolean active) {
        return varietyTypeService.editVarietyType(id, description, active);
    }
}
