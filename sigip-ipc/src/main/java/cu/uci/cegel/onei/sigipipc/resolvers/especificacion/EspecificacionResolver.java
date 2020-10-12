package cu.uci.cegel.onei.sigipipc.resolvers.especificacion;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import cu.uci.cegel.onei.sigipipc.services.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "EspecificacionResolver")
public class EspecificacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private SpecificationService specificationService;

    //Queries
    public List<Specification> specifications() {
        return specificationService.findAll();
    }

    public Specification specificationByDescription(String descripcion) {
        return specificationService.specificationByDescription(descripcion);
    }

    public List<Specification> specificationListByCharacteristic(Long characteristicId){
        return specificationService.specificationListByCharacteristic(characteristicId);
    }

    public List<Specification> specificationListActiveDeUM(){
        return specificationService.specificationListActiveDeUM();
    }

    public List<Specification> specificationListActiveDeCantidad(){
        return specificationService.specificationListActiveDeCantidad();  }

    //Mutations
    public Specification addSpecification(String description, Long characteristicId) {
        return specificationService.add(description, characteristicId);
    }

    public Boolean deleteSpecification(Long id) {
        return specificationService.delete(id);
    }

    public Boolean deleteSpecifications(List<Long> idSpecifications) {
        return specificationService.deleteSpecification(idSpecifications);
    }




}
