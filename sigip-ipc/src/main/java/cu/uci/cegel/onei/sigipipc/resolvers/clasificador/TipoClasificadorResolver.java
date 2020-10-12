package cu.uci.cegel.onei.sigipipc.resolvers.clasificador;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.ClassifierType;
import cu.uci.cegel.onei.sigipipc.services.ClassifierTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "TipoClasificadorResolver")
public class TipoClasificadorResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private ClassifierTypeService classifierTypeService;

    //Queries
    public List<ClassifierType> classifierTypes() {
        return classifierTypeService.findAll();
    }

    public List<ClassifierType> classifierTypesPaging(int page, int size) {
        return classifierTypeService.classifierTypesPaging(page, size);
    }

    public ClassifierType classifierTypeById(Long id) {
        return classifierTypeService.classifierTypeById(id);
    }

    public int totalClassifierTypes() {
        return classifierTypeService.totalClassifierTypes();
    }

    public List<ClassifierType> classifierTypeByDescriptionContains(String description, int page, int size) {
        return classifierTypeService.findByDescriptionContains(description, page, size);
    }

    public int totalClassifierTypesByDescriptionContains(String desciption) {
        return classifierTypeService.totalByDescriptionContains(desciption);
    }

    //Mutations
    public ClassifierType addClassifierTypes(String description, Boolean active, String codeClassifier) {
        return classifierTypeService.addClassifierTypes(description, active, codeClassifier);
    }

    public ClassifierType editClassifierTypes(Long id, String description, Boolean active, String codeClassifier) {
        return classifierTypeService.editClassifierTypes(id, description, active, codeClassifier);
    }
}
