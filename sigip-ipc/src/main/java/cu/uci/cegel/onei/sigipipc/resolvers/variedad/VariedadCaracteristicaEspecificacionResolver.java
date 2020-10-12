package cu.uci.cegel.onei.sigipipc.resolvers.variedad;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResource;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;
import cu.uci.cegel.onei.sigipipc.services.VarietyCharactSpecificService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "VariedadCaracteristicaEspecificacionResolver")
public class VariedadCaracteristicaEspecificacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private VarietyCharactSpecificService varietyCharactSpecificService;

    public List<VarietyCharactSpecific> findByVarietyCharactSpecificActive(Long classifierId) {
        return varietyCharactSpecificService.findByVarietyCharactSpecificActive(classifierId);
    }


    List<CaracteristicaEspecificacionResource> caracteristicasSpecsByVarEstabId(Long id) {
        return varietyCharactSpecificService.caracteristicasSpecsByVarEstabId(id);
    }

    List<CaracteristicaEspecificacionResource> caracteristicasSpecsByCatchmentId(Long id) {
        return varietyCharactSpecificService.caracteristicasSpecsByCatchmentId(id);
    }
}
