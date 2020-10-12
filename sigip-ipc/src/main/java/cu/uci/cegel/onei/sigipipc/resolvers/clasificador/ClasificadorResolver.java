package cu.uci.cegel.onei.sigipipc.resolvers.clasificador;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.ClassifierInput;
import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import cu.uci.cegel.onei.sigipipc.services.ClassifierService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Resolver(name = "ClasificadorResolver")
public class ClasificadorResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    ClassifierService classifierService;

    // Queries
    public Classifier classifier(String description) {
        return classifierService.findOneByDescription(description);
    }

    public List<Classifier> classifiers(Long id) {
        if (Objects.isNull(id)) {
            return classifierService.getAll();
        }
        return classifierService.classifierById(id);
    }

    public List<Classifier> allClassifierXClassifierType(Long id_Classifier_Type) {
        return classifierService.findAllByClassifierTypeEquals(id_Classifier_Type);
    }

    public List<Classifier> allClassifierXClassifierTypePaging(Long id_Classifier_Type, int page, int size) {
        return classifierService.findAllByClassifierTypeEquals(id_Classifier_Type, page, size);
    }

    public Classifier classifierById(Long id) {
         return classifierService.findById(id);
    }

    public int totalClassifierXClassifierType(Long id) {
        return classifierService.totalClassifierXClassifierType(id);
    }

    public List<Classifier> classifierByMarket( Long id_Market, Long id_Establishment,  int size, int page) {
        return classifierService.classifierByMarket(id_Market, id_Establishment, size, page);
    }

    public int totalClassifierByMarket( Long id_market, Long id_Establishment) {
        return classifierService.totalClassifierByMarket(id_market, id_Establishment);
    }

    // Mutations
    public Classifier manageClassifier(ClassifierInput input) {
        return classifierService.manageClassifier(input);
    }

    public Long deleteClassifier(Long id) {
        return classifierService.deleteClassifier(id);
    }

    public List<Classifier> variedadesFiltradas(String descripcion, int page, int size) {
        return classifierService.filters(descripcion, page, size);
    }

    public List<Classifier> variedadesFiltradasByMarket( Long id_Market, Long id_Establishment,  int size, int page, String descripcion) {
        return classifierService.variedadesFiltradasByMarket(id_Market, id_Establishment, size, page, descripcion );
    }

    public Integer totalvariedadesFiltradasByMarket(Long id_Market, Long id_Establishment, String descripcion) {
        return classifierService.totalvariedadesFiltradasByMarket(id_Market, id_Establishment, descripcion);
    }

    public List<Classifier> articulosFiltrados(String descripcion, int page, int size) {
        return classifierService.filters(descripcion, page, size,5);
    }
}
